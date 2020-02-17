package com.gagan.banking.rest.response;

import org.springframework.stereotype.Component;

@Component
public class ResponseEntity {

	private Object result;
	private boolean error;
	
	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

}
