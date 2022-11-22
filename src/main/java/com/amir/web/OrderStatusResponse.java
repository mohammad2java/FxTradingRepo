package com.amir.web;

import java.util.concurrent.atomic.AtomicInteger;

import com.amir.model.GenericResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderStatusResponse extends GenericResponse {
	private static final long serialVersionUID = 5268314177840418888L;
	private AtomicInteger orderId;
	private String status;
}
