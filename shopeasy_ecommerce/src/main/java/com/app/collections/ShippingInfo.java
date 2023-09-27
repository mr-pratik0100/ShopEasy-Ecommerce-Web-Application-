package com.app.collections;

import lombok.Data;

@Data
public class ShippingInfo {
	private String address;
	private String city;
	private String state;
	private String country;
	private Long pincode;
	private Long phoneno;
}
