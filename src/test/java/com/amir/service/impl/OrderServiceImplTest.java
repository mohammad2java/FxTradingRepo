package com.amir.service.impl;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.amir.model.CreateOrderRequest;
import com.amir.model.CreateOrderResponse;
import com.amir.model.FxOrder;
import com.amir.model.OrderNotFoundException;
import com.amir.model.OrderStatus;
import com.amir.web.OrderStatusResponse;

public class OrderServiceImplTest {

	private OrderServiceImpl orderServiceImpl;
	private FxRepo fxRepo;
	private CreateOrderRequest request;
	private ArrayList<FxOrder> orders = new ArrayList<>();

	@Before
	public void init() {
		request = buidRequest();
		System.out.println("init calling");
		FxOrder fxOrder =  new FxOrder();
		fxOrder.setRequest(request);
		fxOrder.setOrderId(new AtomicInteger(1));
		fxOrder.setStatus(OrderStatus.PENDING.getCode());
		orders.add(fxOrder );
		fxRepo = Mockito.mock(FxRepo.class);
		orderServiceImpl = new OrderServiceImpl(fxRepo, new PairFinderServiceImpl());
	}

	@Test
	public void createOrder_SellUSD_OrderID_1_PASS() {
		Mockito.when(fxRepo.getOrders()).thenReturn(new ArrayList<>());
		CreateOrderResponse createOrder = orderServiceImpl.createOrder(request);
		assertTrue(createOrder.getOrderId().get() == 1);
	}

	@Test
	public void createOrder_SellUSD_OrderID_0_FAILED() {
		Mockito.when(fxRepo.getOrders()).thenReturn(new ArrayList<>());
		CreateOrderResponse createOrder = orderServiceImpl.createOrder(request);
		assertTrue(createOrder.getOrderId().get() != 0);
	}

	@Test(expected = OrderNotFoundException.class)
	public void SearchOrder_SellUSD_OrderID_1_NOTFOUND() {
		Mockito.when(fxRepo.getOrders()).thenReturn(new ArrayList<>());
		orderServiceImpl.searchOrder(Integer.valueOf(1));
	}
	
	
	@Test
	public void SearchOrder_SellUSD_OrderID_1_FOUND() {
		Mockito.when(fxRepo.getOrders()).thenReturn(orders);
		OrderStatusResponse searchOrder = orderServiceImpl.searchOrder(Integer.valueOf(1));
		assertTrue(searchOrder.getOrderId().get()==1);
	}
	

	private CreateOrderRequest buidRequest() {
		CreateOrderRequest request = new CreateOrderRequest();
		request.setAmount(10);
		request.setBuyingCurrenyId(1);
		request.setCurrencyId(2);
		return request;
	}

}
