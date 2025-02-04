package com.readmoree.service;

import com.readmoree.dto.ApiResponse;
import com.readmoree.entities.Payment;

public interface PaymentService {
	
	ApiResponse savePayment(Payment payment);

}
