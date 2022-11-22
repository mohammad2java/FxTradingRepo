package com.amir.model;

import java.math.BigDecimal;
import java.util.Arrays;

public enum FxCurrencies {
	
	USD(BigDecimal.valueOf(1),1), GBP(BigDecimal.valueOf(0.5),2);
	private BigDecimal ratio;
	private Integer code;

	FxCurrencies(BigDecimal ratio, int code) {
		this.ratio = ratio;
		this.code = code;
	}

	public BigDecimal getRatio() {
		return ratio;
	}

	public static BigDecimal multiplyFactor(FxCurrencies from, FxCurrencies to) {
		BigDecimal fromR = FxCurrencies.USD.getRatio().divide(from.getRatio());
		BigDecimal toR = FxCurrencies.USD.getRatio().divide(to.getRatio());
		return fromR.divide(toR);
	}
	
	public static BigDecimal multiplyFactor(Integer from, Integer to) {
		FxCurrencies fromR = Arrays.stream(FxCurrencies.values()).filter(s->s.getCode().equals(from)).findFirst().get();
		FxCurrencies toR = Arrays.stream(FxCurrencies.values()).filter(s->s.getCode().equals(to)).findFirst().get();
		return multiplyFactor(fromR,toR);
	}

	public Integer getCode() {
		return code;
	}
}
