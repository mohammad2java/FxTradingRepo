package com.amir.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.amir.model.CreateOrderRequest;
import com.amir.model.CreateOrderResponse;
import com.amir.model.FxCurrencies;
import com.amir.model.FxOrder;
import com.amir.model.OrderNotFoundException;
import com.amir.model.OrderStatus;
import com.amir.model.OrderStatusResponse;
import com.amir.service.OrderService;
import com.amir.service.PairFinderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

	private final FxRepo fxRepo;
	private final PairFinderService pairFinderService;

	@Override
	public CreateOrderResponse createOrder(CreateOrderRequest request) {
		log.info("create order process started..");
		CreateOrderResponse ret = new CreateOrderResponse();
		FxOrder fxOrder = new FxOrder();
		List<FxOrder> orders = fxRepo.getOrders();
		if (orders.isEmpty()) {
			fxOrder.setOrderId(new AtomicInteger(1));
			orders.add(fxOrder);
			fxOrder.setStatus(OrderStatus.PENDING.getCode());
			fxOrder.setRequest(request);
			ret.setOrderId(fxOrder.getOrderId());
			patchTime(request, ret);
			return ret;
		}
		AtomicInteger orderId = orders.get(orders.size() - 1).getOrderId();
		ret.setOrderId(new AtomicInteger(orderId.get()+1));
		fxOrder.setOrderId(ret.getOrderId());
		fxOrder.setRequest(request);
		orders.add(fxOrder);
		String status = calcStatus(orders, request);
		fxOrder.setStatus(status);
		patchTime(request, ret);
		log.info("create order process finished..");
		return ret;
	}

	private String calcStatus(List<FxOrder> orders, CreateOrderRequest request) {

		BigDecimal mulFactor = FxCurrencies.multiplyFactor(request.getCurrencyId(), request.getBuyingCurrenyId());
		int requiredAmount = mulFactor.multiply(BigDecimal.valueOf(request.getAmount())).intValue();
		List<FxOrder> list = orders.stream()
				.filter(s -> s.getRequest().getCurrencyId().equals(request.getBuyingCurrenyId()))
				.collect(Collectors.toList());
		if (list.isEmpty()) {
			return OrderStatus.PENDING.getCode();
		}
		Map<Integer, Set<Set<Integer>>> pairs = pairFinderService.calcAllPossiblePairs(new int[list.size()]);
		List<Set<Integer>> listPairs = pairs.values().stream().flatMap(s -> s.stream()).collect(Collectors.toList());
		Optional<Integer> findAny = listPairs.stream().map(s-> {
			Integer sum = s.stream().map(s1->{
				FxOrder fxOrder = list.get(s1.intValue());
				return fxOrder.getRequest().getAmount();
			}).collect(Collectors.summingInt(Integer::intValue));
			return sum;	
		}).filter(s -> s.equals(requiredAmount))
				.findAny();
		if (findAny.isPresent()) {
			return OrderStatus.FULLY_MATCH.getCode();
		}
		return OrderStatus.PARTIAL_MATCH.getCode();
	}

	private void patchTime(CreateOrderRequest request, CreateOrderResponse ret) {
		ret.setRequestTime(request.getRequestTime());
		ret.setResponseTime(new Date());
		ret.setResult(1);
	}

	@Override
	public OrderStatusResponse searchOrder(Integer orderId) {
		OrderStatusResponse ret = new OrderStatusResponse();
		Optional<FxOrder> findFirst = fxRepo.getOrders().stream().filter(s->s.getOrderId().get()==orderId.intValue()).findFirst();
		if(!findFirst.isPresent()) {
			throw new OrderNotFoundException(String.valueOf(orderId));
		}
		FxOrder fxOrder = findFirst.get();
		buildRes(ret,fxOrder);
		return ret;
	}

	private void buildRes(OrderStatusResponse ret, FxOrder fxOrder) {
		log.info("found order: {} {} ",fxOrder.getOrderId(),fxOrder.getStatus());
		ret.setResult(1);
		ret.setOrderId(fxOrder.getOrderId());
		ret.setStatus(fxOrder.getStatus());
	}



}
