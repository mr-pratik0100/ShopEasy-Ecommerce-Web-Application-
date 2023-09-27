package com.app.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.collections.Order;
import com.app.custom_exceptions.ErrorHandler;
import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.custom_exceptions.UserNotFoundException;
import com.app.dto.OrderDto;
import com.app.service.OrderService;

@RestController
@RequestMapping("/order")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {
	@Autowired
	private OrderService orderservice;

	@PostMapping("/create")
	public String createOrder(@RequestBody OrderDto orderdto, HttpSession session) throws ErrorHandler {
		return orderservice.createOrder(orderdto, session);
	}

	@GetMapping("/admin/all")
	public List<Order> getAllOrders(HttpSession session) throws ErrorHandler {
		return orderservice.getAllOrder(session);
	}

	@GetMapping("/{id}")
	public Order getOrderById(@PathVariable String id, HttpSession session)
			throws ResourceNotFoundException, ErrorHandler {
		return orderservice.getOrderById(id, session);
	}

	@DeleteMapping("/admin/delete/{id}")
	public String deleteOrder(@PathVariable String id, HttpSession session) throws ErrorHandler {
		return orderservice.deleteOrder(id, session);
	}

	@PutMapping("/admin/{id}")
	public Order updateOrderStatus(@PathVariable String id, @RequestBody String status, HttpSession session)
			throws ErrorHandler {
		return orderservice.updateOrderStatus(id, status, session);
	}

	@GetMapping("/me")
	public List<Order> getMyOrders(HttpSession session) throws UserNotFoundException {
		return orderservice.getMyOrders(session);
	}

}
