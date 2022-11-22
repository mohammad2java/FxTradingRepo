package com.amir.model;

import java.util.concurrent.atomic.AtomicInteger;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderResponse extends GenericResponse {
	private static final long serialVersionUID = 5333905643732266019L;
	private AtomicInteger orderId;
	

}
