package com.jsp.sagar_shopee.respone;

import lombok.Data;

@Data
public class ApiResponse {

	private String msg;
	private Object data;

	public ApiResponse(String msg, Object data) {
		super();
		this.msg = msg;
		this.data = data;
	}

}
