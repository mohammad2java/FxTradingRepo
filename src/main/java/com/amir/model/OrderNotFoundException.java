package com.amir.model;

public class OrderNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -3356754553559216602L;	
	public OrderNotFoundException(String msg) {
		super(msg);
	}

}
