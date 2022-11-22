package com.amir.model;

import java.util.Objects;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderRequest extends GenericRequest {
	private static final long serialVersionUID = -8245280171058376720L;
	@NotNull(message = "ERR001=amount is mandarory field")
	@Min(value = 1, message = "ERR002=amount ${validatedValue} is invalid, should not be less than {value}")
	@ApiModelProperty(required = true)
	private Integer amount;
	@NotNull(message = "ERR001=currencyId is mandarory field")
	@Range(min = 1, max = 2, message = "ERR002= currencyId ${validatedValue} is invalid code")
	@ApiModelProperty(required = true)
	private Integer currencyId;
	@NotNull(message = "ERR001=buyingCurrenyId is mandarory field")
	@Range(min = 1, max = 2, message = "ERR002=buyingCurrenyId ${validatedValue} is invalid code")
	@ApiModelProperty(required = true)
	private Integer buyingCurrenyId;
	
	@AssertFalse(message = "ERR003=buyingCurrenyId and currencyId could not be same")
	public boolean isBothCurrencyIdSame() {
		if(Objects.nonNull(currencyId) && Objects.nonNull(buyingCurrenyId)) {
			return currencyId.equals(buyingCurrenyId);
		}
		 return false;
	}
}
