package com.app.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.app.collections.Order;
import com.app.custom_exceptions.ErrorHandler;
import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.custom_exceptions.UserNotFoundException;
import com.app.dto.OrderDto;

public interface OrderService {
	public String createOrder(OrderDto orderdto, HttpSession session) throws ErrorHandler;

	public String deleteOrder(String id, HttpSession session) throws ErrorHandler;

	public List<Order> getAllOrder(HttpSession session) throws ErrorHandler;

	public Order getOrderById(String id, HttpSession session) throws ResourceNotFoundException, ErrorHandler;

	public Order updateOrderStatus(String id, String status, HttpSession session) throws ErrorHandler;

	public List<Order> getMyOrders(HttpSession session) throws UserNotFoundException;

}
