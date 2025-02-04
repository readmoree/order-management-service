package com.readmoree.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.readmoree.dao.PaymentDao;
import com.readmoree.dto.ApiResponse;
import com.readmoree.entities.Payment;
import com.readmoree.service.PaymentService;

import lombok.AllArgsConstructor;



@Service
@Transactional
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {
	
	private PaymentDao paymentDao;

	@Override
	public ApiResponse savePayment(Payment payment) {
		Payment savedPayment = paymentDao.save(payment);
		return new ApiResponse("Payment saved successfully with id: "+savedPayment.getPaymentId());
	}

}
