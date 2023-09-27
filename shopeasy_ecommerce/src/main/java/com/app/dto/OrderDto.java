package com.app.dto;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.app.collections.OrderItems;
import com.app.collections.PaymentInfo;
import com.app.collections.ShippingInfo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderDto {
	private ShippingInfo shippinginfo;
	private PaymentInfo paymentinfo;
	private LocalDate date;
	@NotBlank(message="plz enter item price")
	private double itemPrice;
	private double taxPrice;
	@NotBlank(message="plz enter shipping price")
	private double shippingPrice;
	@NotBlank(message="plz enter total price")
	private double totalPrice;
	private String orderStatus;
	private List<OrderItems>orderItems;
	
	//user not added 
}
