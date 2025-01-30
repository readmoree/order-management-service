package com.readmoree.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.readmoree.dao.OrderDao;
import com.readmoree.dto.OrderDto;
import com.readmoree.service.OrderService;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
	
	private OrderDao orderDao;
	
//	private ModelMapper modelMapper;

	@Override
	public List<OrderDto> getAllOrders(Long userId) {
		 
		//validate userId
	
		List<OrderDto> results = (List<OrderDto>)orderDao.findOrderDetailsByCustomer(userId);
		
		return results;
	}

}
