package com.amir.web;


import java.util.Date;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amir.model.CreateOrderRequest;
import com.amir.model.CreateOrderResponse;
import com.amir.service.OrderService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@RestController
@Api(tags = "OrderController")
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
	
  private final OrderService orderService;
	
	@PostMapping("/create")
	public ResponseEntity<CreateOrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request) {
		doBaseJob(request);
		CreateOrderResponse ret =  orderService.createOrder(request);
		return  ResponseEntity.status(HttpStatus.CREATED).body(ret);
	}

	
	@GetMapping("/{orderId}")
	public ResponseEntity<OrderStatusResponse> searchOrder(@PathVariable("orderId") Integer orderId) {
		Objects.requireNonNull(orderId, "OrderId Cant be null");
		OrderStatusResponse ret = new OrderStatusResponse();
		patchTime(ret);
		OrderStatusResponse res =  orderService.searchOrder(orderId);
		patchResTime(ret, res);
		return  ResponseEntity.ok().body(res);
	}


	private void patchResTime(OrderStatusResponse ret, OrderStatusResponse res) {
		res.setResponseTime(new Date());
		res.setRequestTime(ret.getRequestTime());
	}


	private void patchTime(OrderStatusResponse ret) {
		ret.setRequestTime(new Date());
	}
	
	private void doBaseJob(CreateOrderRequest request) {
		request.setRequestTime(new Date());
	}

	
	
	
}
