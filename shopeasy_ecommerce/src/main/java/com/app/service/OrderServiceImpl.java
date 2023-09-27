package com.app.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.collections.Order;
import com.app.collections.User;
import com.app.custom_exceptions.ErrorHandler;
import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.custom_exceptions.UserNotFoundException;
import com.app.dto.OrderDto;
import com.app.repository.OrderRepository;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderrepository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public String createOrder(OrderDto orderdto, HttpSession session) throws ErrorHandler {
		User storedUser = (User) session.getAttribute("user");

		if (storedUser == null) {
			throw new ErrorHandler("To Order this Product Login first");
		}

		Order order = mapper.map(orderdto, Order.class);
		order.setUser(storedUser.getId());
		orderrepository.save(order);
		return "Ordered Successfully";
	}

	@Override
	public String deleteOrder(String id, HttpSession session) throws ErrorHandler {
		String mesg = "Please enter a valid id";

		// Authorize user("Admin")
		User storedUser = (User) session.getAttribute("user");
		String userRole = storedUser.getRole();
		if (userRole.equals("Admin")) {

		} else {
			throw new ErrorHandler(storedUser.getName() + " is not allowed to access this resource");
		}

		if (orderrepository.existsById(id)) {
			orderrepository.deleteById(id);
			mesg = "Order deleted successfully";
		}
		return mesg;
	}

	@Override
	public List<Order> getAllOrder(HttpSession session) throws ErrorHandler {

		// Authorize user("Admin")
		User storedUser = (User) session.getAttribute("user");
		String userRole = storedUser.getRole();
		if (userRole.equals("Admin")) {

		} else {
			throw new ErrorHandler(storedUser.getName() + " is not allowed to access this resource");
		}

		return orderrepository.findAll();
	}

	@Override
	public Order getOrderById(String id, HttpSession session) throws ResourceNotFoundException, ErrorHandler {

		User storedUser = (User) session.getAttribute("user");

		if (storedUser == null) {
			throw new ErrorHandler("To Order this Product Login first");
		}

		return orderrepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order Not Found"));
	}

	@Override
	public Order updateOrderStatus(String id, String status, HttpSession session) throws ErrorHandler {
		// Authorize user("Admin")
		User storedUser = (User) session.getAttribute("user");
		String userRole = storedUser.getRole();
		if (userRole.equals("Admin")) {

		} else {
			throw new ErrorHandler(storedUser.getName() + " is not allowed to access this resource");
		}

		Optional<Order> opOrder = orderrepository.findById(id);
		Order order = opOrder.get();
		order.setOrderStatus(status);
		orderrepository.save(order);

		return null;
	}

	@Override
	public List<Order> getMyOrders(HttpSession session) throws UserNotFoundException {
		User storedUser = (User) session.getAttribute("user");
		if (storedUser == null) {
			throw new UserNotFoundException("User Not found");
		}
		
		return orderrepository.findByUser(storedUser.getId());
		
	}

	// MY ORDERS

}
