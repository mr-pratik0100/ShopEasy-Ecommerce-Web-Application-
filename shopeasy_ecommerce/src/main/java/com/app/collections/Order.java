package com.app.collections;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@Document(collection ="order")
public class Order {
	
	private String id;
	private String user;
	private ShippingInfo shippinginfo;
	private PaymentInfo paymentinfo;
	private LocalDate date=LocalDate.now();
	private double itemPrice;
	private double taxPrice;
	private double shippingPrice;
	private double totalPrice;
	private String orderStatus;
	private List<OrderItems>orderItems=new ArrayList<OrderItems>();
	
	public Order() {
		
	}

	public Order(ShippingInfo shippinginfo, PaymentInfo paymentinfo, double itemPrice, double taxPrice,
			double shippingPrice, double totalPrice, String orderStatus) {
		super();
		this.shippinginfo = shippinginfo;
		this.paymentinfo = paymentinfo;
		this.itemPrice = itemPrice;
		this.taxPrice = taxPrice;
		this.shippingPrice = shippingPrice;
		this.totalPrice = totalPrice;
		this.orderStatus = orderStatus;
	}
	
	
}
