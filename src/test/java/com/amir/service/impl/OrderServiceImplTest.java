package com.amir.service.impl;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.amir.model.CreateOrderRequest;
import com.amir.model.CreateOrderResponse;
import com.amir.model.FxOrder;
import com.amir.model.OrderNotFoundException;
import com.amir.model.OrderStatus;
import com.amir.model.OrderStatusResponse;

public class OrderServiceImplTest {

	private OrderServiceImpl orderServiceImpl;
	private FxRepo fxRepo;
	private CreateOrderRequest request;
	private ArrayList<FxOrder> orders = new ArrayList<>();

	@Before
	public void init() {
		request = buidRequest(10,2,1);
		FxOrder fxOrder =  new FxOrder();
		fxOrder.setRequest(request);
		fxOrder.setOrderId(new AtomicInteger(1));
		fxOrder.setStatus(OrderStatus.PENDING.getCode());
		orders.add(fxOrder );
		fxRepo = Mockito.mock(FxRepo.class);
		orderServiceImpl = new OrderServiceImpl(fxRepo, new PairFinderServiceImpl());
	}

	@Test
	public void createOrder_SellGBP_OrderID_1_PASS() {
		Mockito.when(fxRepo.getOrders()).thenReturn(new ArrayList<>());
		CreateOrderResponse createOrder = orderServiceImpl.createOrder(request);
		assertTrue(createOrder.getOrderId().get() == 1);
	}

	@Test(expected = OrderNotFoundException.class)
	public void SearchOrder_SellUSD_OrderID_1_NOTFOUND() {
		Mockito.when(fxRepo.getOrders()).thenReturn(new ArrayList<>());
		orderServiceImpl.searchOrder(Integer.valueOf(1));
	}
	
	
	@Test
	public void SearchOrder_SellGBP_OrderID_1_FOUND() {
		Mockito.when(fxRepo.getOrders()).thenReturn(orders);
		OrderStatusResponse searchOrder = orderServiceImpl.searchOrder(Integer.valueOf(1));
		assertTrue(searchOrder.getOrderId().get()==1);
	}
	
	@Test
	public void createOrder_SellUSD_OrderID_2_FULLYMATCHED() {
		Mockito.when(fxRepo.getOrders()).thenReturn(orders);
		CreateOrderResponse createOrder = orderServiceImpl.createOrder(buidRequest(20, 1, 2));
		OrderStatusResponse searchOrder = orderServiceImpl.searchOrder(createOrder.getOrderId().get());
		System.out.println(searchOrder.getStatus());
		assertTrue(StringUtils.equalsIgnoreCase(searchOrder.getStatus(), OrderStatus.FULLY_MATCH.getCode()));
	}
	
	
	@Test
	public void createOrder_SellUSD_OrderID_2_PARTIALLY_MATCHED() {
		Mockito.when(fxRepo.getOrders()).thenReturn(orders);
		CreateOrderResponse createOrder = orderServiceImpl.createOrder(buidRequest(10, 1, 2));
		OrderStatusResponse searchOrder = orderServiceImpl.searchOrder(createOrder.getOrderId().get());
		System.out.println(searchOrder.getStatus());
		assertTrue(StringUtils.equalsIgnoreCase(searchOrder.getStatus(), OrderStatus.PARTIAL_MATCH.getCode()));
	}
	
	@Test
	public void createOrder_SellUSD_OrderID_1_PENDING() {
		Mockito.when(fxRepo.getOrders()).thenReturn(new ArrayList<>());
		CreateOrderResponse createOrder = orderServiceImpl.createOrder(request);
		OrderStatusResponse searchOrder = orderServiceImpl.searchOrder(createOrder.getOrderId().get());
		System.out.println(searchOrder.getStatus());
		assertTrue(StringUtils.equalsIgnoreCase(searchOrder.getStatus(), OrderStatus.PENDING.getCode()));
	}
	
	private CreateOrderRequest buidRequest(int amount, int currId, int buyCurrId) {
		CreateOrderRequest request = new CreateOrderRequest();
		request.setAmount(amount);
		request.setBuyingCurrenyId(buyCurrId);
		request.setCurrencyId(currId);
		return request;
	}

}
