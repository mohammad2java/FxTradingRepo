package com.amir.model;

public enum OrderStatus {
	PENDING("PENDING"), PARTIAL_MATCH("PARTIAL_MATCHED"), FULLY_MATCH("FULLY_MATCHED");

	private String code;

	OrderStatus(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
