package com.jsp.sagar_shopee.exception;

import org.springframework.stereotype.Controller;

public class ProductNotFoundException extends RuntimeException {

	public ProductNotFoundException(String msg) {
		super(msg);
	}
}
