package com.readmoree.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ApiResponse {
	
	private LocalDateTime timeStamp;
	private String message;
	private String orderId;
	private List<OrderDetailsDto> orderDetailsDtos=new ArrayList<OrderDetailsDto>(); 
	
	public ApiResponse(String message) {
		this.message = message;
		this.timeStamp=LocalDateTime.now();
	}
	public ApiResponse(String message, String orderId) {
		this.message = message;
		this.orderId = orderId;
		this.timeStamp=LocalDateTime.now();
	}
	public ApiResponse(String message, List<OrderDetailsDto> orderDetailsDtos) {
		this.message = message;
		this.timeStamp=LocalDateTime.now();
		this.orderDetailsDtos=orderDetailsDtos;
	}
	public ApiResponse(String message,String orderId, List<OrderDetailsDto> orderDetailsDtos) {
		this.message = message;
		this.orderId = orderId;
		this.timeStamp=LocalDateTime.now();
		this.orderDetailsDtos=orderDetailsDtos;
	}
	
}