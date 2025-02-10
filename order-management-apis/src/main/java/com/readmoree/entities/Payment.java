package com.readmoree.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
public class Payment {
	
	@Id
	@Column(name="payment_id")
	private Long paymentId;

	@OneToOne
	@JoinColumn(name="order_id")
	private Order order;

	@Column(name="payment_method")
	private String paymentMethod;

	@Enumerated(EnumType.STRING)
	@Column(name="payment_status", columnDefinition = "VARCHAR(30)")
	private PaymentStatus paymentStatus;

	public static Long generatePaymentId() {
		return (long)(Math.random() * Long.MAX_VALUE);  // Generates a random payment ID
	}

	public Payment(Long paymentId, String paymentMethod, PaymentStatus paymentStatus) {
		this.paymentId = paymentId;
		this.paymentMethod = paymentMethod;
		this.paymentStatus = paymentStatus;
	}

	public static PaymentStatus assignPaymentStatus(String paymentMethod, Long paymentId) {

		switch (paymentMethod) {
		case "COD":
		case "Cash On Delivery":
		case "Pay on delivery":{
			return PaymentStatus.PENDING;
		}
		case "DEBIT CARD":
		case "CREDIT CARD":
		case "UPI":
		case "NET BANKING":{
			if(paymentId != null) {
				return PaymentStatus.PAID;
			}else {
				return PaymentStatus.FAILED;
			}
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + paymentMethod);
		}
	}


}
