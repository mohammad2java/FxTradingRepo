package com.amir.service;

import com.amir.model.CreateOrderRequest;
import com.amir.model.CreateOrderResponse;
import com.amir.web.OrderStatusResponse;

public interface OrderService {

	CreateOrderResponse createOrder(CreateOrderRequest request);
	OrderStatusResponse searchOrder(Integer orderId);

}
