package com.readmoree.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.readmoree.dto.OrderDetailsDto;
import com.readmoree.entities.OrderStatus;
import com.readmoree.service.OrderDetailsService;
import com.readmoree.service.OrderService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/orders")
@CrossOrigin(origins = "http://0.0.0.0:3000")
public class OrderController {

	private OrderDetailsService orderDetailsService;
	private OrderService orderService;

	private Integer getCustomerId() {
		// Retrieve the customerId from the authentication object in the SecurityContext
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Integer customerId = (Integer) authentication.getPrincipal(); // customerId is set as the principal 
		return customerId;
	}

	//place order: many books
	@PostMapping("/placeOrder")
	public ResponseEntity<?> placeOrder(@RequestParam Integer addressId, @RequestParam String paymentMethod ,@Valid @RequestBody List<OrderDetailsDto> orderDetailsDtoList){
		return ResponseEntity.ok(orderDetailsService.createOrder(getCustomerId(), addressId, paymentMethod, orderDetailsDtoList));
	}

	//cancel order
	@PatchMapping("/cancel")
	public ResponseEntity<?> cancelOrder(@RequestBody String orderId){
		return ResponseEntity.ok(orderDetailsService.cancelOrder(getCustomerId(),orderId));
	}

	//return order
	@PatchMapping("/return")
	public ResponseEntity<?> returnOrder(@RequestBody String orderId){
		return ResponseEntity.ok(orderDetailsService.returnOrder(getCustomerId(),orderId));
	}

	//exchange order
	@PatchMapping("/exchange")
	public ResponseEntity<?> exchangeOrder(@RequestBody String orderId){
		return ResponseEntity.ok(orderDetailsService.exchangeOrder(getCustomerId(),orderId));
	}

	//display all orders
	@GetMapping("/list")
	public ResponseEntity<?> getAllOrdersByUserId(){
		return ResponseEntity.ok(orderService.getAllOrders(getCustomerId()));
	}

	//get all orders
	@GetMapping("/admin/allOrders")
	public ResponseEntity<?> getAllOrders(){
		return ResponseEntity.ok(orderService.getAllOrders());	
	}

	@PatchMapping("/admin/orderStatus/{orderId}")
	public ResponseEntity<?> updateOrderStatus(@PathVariable String orderId,@RequestParam OrderStatus orderStatus){
		return ResponseEntity.ok(orderService.updateOrderStatus(orderId,orderStatus));
	}

}
