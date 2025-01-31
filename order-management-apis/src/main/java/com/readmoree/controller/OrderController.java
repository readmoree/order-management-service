package com.readmoree.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.readmoree.dto.OrderDetailsDto;
import com.readmoree.service.OrderDetailsService;
import com.readmoree.service.OrderService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/orders")
public class OrderController {
	
	private OrderDetailsService orderDetailsService;
	private OrderService orderService;
	
	
	//place order: many books
	@PostMapping("/place/{userId}")
	public ResponseEntity<?> placeOrder(@PathVariable Long userId, @RequestParam Long addressId, @Valid @RequestBody List<OrderDetailsDto> orderDetailsDtoList){
		return ResponseEntity.ok(orderDetailsService.createOrder(userId, addressId, orderDetailsDtoList));
	}
	
	//cancel order
	@PatchMapping("/cancel/{userId}")
	public ResponseEntity<?> cancelOrder(@PathVariable Long userId, @RequestBody String orderId){
		return ResponseEntity.ok(orderDetailsService.cancelOrder(userId,orderId));
	}
	
	//return order
	@PatchMapping("/return/{userId}")
	public ResponseEntity<?> returnOrder(@PathVariable Long userId, @RequestBody String orderId){
		return ResponseEntity.ok(orderDetailsService.returnOrder(userId,orderId));
	}
	
	//exchange order
	@PatchMapping("/exchange/{userId}")
	public ResponseEntity<?> exchangeOrder(@PathVariable Long userId, @RequestBody String orderId){
		return ResponseEntity.ok(orderDetailsService.exchangeOrder(userId,orderId));
	}
	
	//display all orders
	@GetMapping("/{userId}")
	public ResponseEntity<?> getAllOrdersByUserId(@PathVariable Long userId){
		return ResponseEntity.ok(orderService.getAllOrders(userId));
	}

}
