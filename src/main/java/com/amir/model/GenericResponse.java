package com.amir.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(Include.NON_NULL)
public class GenericResponse implements Serializable {
	private static final long serialVersionUID = 522138352858978619L;
	private Date requestTime;
	private Date responseTime;
	private Integer result;
	private List<FxError> errors;

	public static GenericResponse buildFailedRes() {
		GenericResponse res = new GenericResponse();
		res.setRequestTime(new Date());
		res.setResponseTime(new Date());
		res.setResult(0);
		res.setErrors(new ArrayList<>());
		return res;

	}
}
