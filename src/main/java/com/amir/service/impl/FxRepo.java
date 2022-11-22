package com.amir.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.amir.model.FxOrder;

import lombok.Getter;

@Component
@Getter
public class FxRepo {
	private List<FxOrder> orders =  new ArrayList<>();
}
