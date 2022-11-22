package com.amir.model;

import java.util.concurrent.atomic.AtomicInteger;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FxOrder {
	private AtomicInteger orderId;
	private String status;
	private CreateOrderRequest request;
}
