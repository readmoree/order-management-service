package com.readmoree.entities;

public enum OrderStatus {
	PENDING,        //Order has been placed but not yet processed.
    PROCESSING,     //Order is being prepared or is in transit.
    SHIPPED,        //Order has been shipped to the customer.
    DELIVERED,      //Order has been delivered to the customer.
    CANCELED,       //Order has been canceled.
    RETURNED,       //Order was returned by the customer.
    EXCHANGED,		//New piece with same order
    REFUNDED,       //Payment for the order has been refunded.
    FAILED,         //Payment or other issues prevented order completion.
    COMPLETED,      //Order has been completed successfully.
    ON_HOLD,        //Order is on hold due to issues like stock, payment, etc.
    AWAITING_PAYMENT //Order is waiting for payment confirmation.
}
