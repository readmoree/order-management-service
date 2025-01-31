package com.readmoree.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//for sending response from server to client
@NoArgsConstructor
@Getter
@Setter
public class ApiResponse {
	
	private LocalDateTime timeStamp;
	private String message;
	private List<OrderDetailsDto> orderDetailsDtos=new ArrayList<OrderDetailsDto>(); 
	
//	public ApiResponse(String message, OrderDetailsDto orderDetails) {
//		super();
//		this.message = message;
//		this.timeStamp=LocalDateTime.now();
//		this.orderDetails = orderDetails;
//	}
	public ApiResponse(String message) {
		this.message = message;
		this.timeStamp=LocalDateTime.now();
	}
	public ApiResponse(String message, List<OrderDetailsDto> orderDetailsDtos) {
		this.message = message;
		this.timeStamp=LocalDateTime.now();
		this.orderDetailsDtos=orderDetailsDtos;
	}
	
}