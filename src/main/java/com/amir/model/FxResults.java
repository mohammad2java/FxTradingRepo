package com.amir.model;

public enum FxResults {
	SUCCESS(1), FAIL(0), ERROR(-1);

	private int code;

	FxResults(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

}
