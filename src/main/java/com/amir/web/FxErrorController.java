package com.amir.web;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.amir.model.FxError;
import com.amir.model.GenericResponse;
import com.amir.model.OrderNotFoundException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class FxErrorController {

	@ExceptionHandler(value = { MethodArgumentNotValidException.class })
	public ResponseEntity<GenericResponse> invalidData(MethodArgumentNotValidException ex, WebRequest request) {
		GenericResponse res = GenericResponse.buildFailedRes();
		List<FxError> errors = res.getErrors();
		List<FxError> errList = ex.getAllErrors().stream().map(FxErrorController::converTOFxErr)
				.collect(Collectors.toList());
		errors.addAll(errList);
		log.error("Bad Request: {} ",ex.getAllErrors());
		return ResponseEntity.badRequest().body(res);
	}


	@ExceptionHandler(value = { OrderNotFoundException.class })
	public ResponseEntity<GenericResponse> errorOnServer(OrderNotFoundException ex, WebRequest request) {
		GenericResponse res = GenericResponse.buildFailedRes();
		List<FxError> errors = res.getErrors();
		FxError fxErr = new FxError("ERR004", MessageFormat.format("orderId {0} is not found in database", ex.getMessage()));
		errors.add(fxErr);
		log.error("OrderNotFoundException: {} ",ExceptionUtils.getStackTrace(ex));
		return ResponseEntity.badRequest().body(res);
	}

	
	
	@ExceptionHandler(value = { RuntimeException.class })
	public ResponseEntity<GenericResponse> errorOnServer(RuntimeException ex, WebRequest request) {
		GenericResponse res = GenericResponse.buildFailedRes();
		List<FxError> errors = res.getErrors();
		FxError fxErr = new FxError("ERR000", ex.toString());
		errors.add(fxErr);
		log.error("Internal Server Error: {} ",ExceptionUtils.getStackTrace(ex));
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
	}

	private static FxError converTOFxErr(ObjectError objecterror1) {
		String code = "ERR001";
		String msg = objecterror1.getDefaultMessage();
		if (StringUtils.contains(msg, "=")) {
			String[] error = StringUtils.split(msg, "=");
			code = error[0];
			msg = error[1];
		}
		FxError fxError = new FxError(code, msg);
		return fxError;
	}

}
