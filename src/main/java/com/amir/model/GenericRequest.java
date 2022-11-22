package com.amir.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GenericRequest implements Serializable {
	private static final long serialVersionUID = 522138352858978619L;
	private Date requestTime;
	
}
