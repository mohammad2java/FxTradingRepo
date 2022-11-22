package com.amir;

import java.util.ArrayList;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.amir.model.CreateOrderRequest;
import com.amir.model.CreateOrderResponse;
import com.amir.service.impl.FxRepo;
import com.amir.service.impl.OrderServiceImpl;

@SpringBootTest
public class SimplifiedFxTradingApplicationTests {

	@Autowired
	private OrderServiceImpl orderServiceImpl;
	@MockBean
	private  FxRepo fxRepo;

	@Test
	public void createOrder_SellUSD() {
		System.out.println(fxRepo);
		System.out.println(orderServiceImpl);
		System.out.println("testing started");
		CreateOrderRequest request = new CreateOrderRequest();
		request.setAmount(10);
		request.setBuyingCurrenyId(1);
		request.setCurrencyId(2);
		Mockito.when(fxRepo.getOrders()).thenReturn(new ArrayList<>());
		CreateOrderResponse createOrder = orderServiceImpl.createOrder(request);
		Assertions.assertThat(createOrder.getOrderId().get()==1);
	}

	public SimplifiedFxTradingApplicationTests() {
		super();
	}

	

}
