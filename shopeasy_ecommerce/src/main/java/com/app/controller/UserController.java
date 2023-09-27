package com.app.controller;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.custom_exceptions.ErrorHandler;
import com.app.custom_exceptions.MalFormedTokenException;
import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.custom_exceptions.TokenExpiredException;
import com.app.custom_exceptions.UserNotFoundException;
import com.app.dto.AuthRequest;
import com.app.dto.AuthResponse;
import com.app.dto.EmailDto;
import com.app.dto.PasswordDto;
import com.app.dto.UpdatePasswordDto;
import com.app.dto.UpdateRole;
import com.app.dto.UpdateUserProfileDto;
import com.app.dto.UserDTO;
import com.app.dto.UserResponseDto;
import com.app.jwt.UserDetailsImpl;
import com.app.service.UserService;
import com.app.util.JwtUtil;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public UserResponseDto loginUser(@RequestBody AuthRequest loginDto, HttpServletResponse response,
			HttpSession session) throws UserNotFoundException {
		return userService.loginUser(loginDto, response, session);
	}

	@PostMapping("/register")
	public UserResponseDto createUser(@Valid @RequestBody UserDTO user, HttpSession session,
			HttpServletResponse response) throws ErrorHandler {
		return userService.createUser(user, session, response);

	}

//	@PutMapping("/update/{id}")
//	public String updateUser(@PathVariable String id, @RequestBody UpdateUserDTO updateUser)
//			throws UserNotFoundException {
//		return userService.updateUser(id, updateUser);
//	}

	@DeleteMapping("/delete/{id}")
	public String deleteUser(@PathVariable String id) throws UserNotFoundException {
		return userService.deleteUser(id);
	}

	@GetMapping("/admin/{id}")
	public UserResponseDto getSingleUser(@PathVariable String id, HttpSession session)
			throws UserNotFoundException, ErrorHandler {
		return userService.getSingleUser(id, session);
	}

	@GetMapping("/admin/all")
	public List<UserDTO> findAllUser(@CookieValue(name = "tokenjwt") String tokenjwt, HttpSession session)
			throws AccessDeniedException, TokenExpiredException, MalFormedTokenException, ResourceNotFoundException,
			ErrorHandler {
		return userService.findAllUser(tokenjwt, session);
	}

	@GetMapping("/logout")
	public ResponseEntity<?> logout(HttpServletResponse res) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.logout(res));
	}

	@PostMapping("/password/forgot")
	public String forgotPassword(@RequestBody EmailDto emailDto) throws UserNotFoundException {
		return userService.forgotPassword(emailDto);
	}

	@PutMapping("/password/reset/{token}")
	public String resetPassword(@PathVariable String token, @RequestBody PasswordDto passwordDto, HttpSession session,
			HttpServletResponse response) throws UserNotFoundException, ErrorHandler {
		System.out.println("reset Controller");
		return userService.resetPassword(token, passwordDto, session, response);
	}

	@GetMapping("/me")
	public UserResponseDto getUserDetails(HttpSession session) throws ErrorHandler {
		return userService.getUserDetails(session);
	}

	@PutMapping("/password/update")
	public UserResponseDto updatePassword(@RequestBody UpdatePasswordDto upDto, HttpSession session)
			throws ErrorHandler, UserNotFoundException {
		return userService.updatePassword(upDto, session);
	}

	@PutMapping("/me/update")
	public String updateUserProfile(@RequestBody UpdateUserProfileDto updateUserProfileDto, HttpSession session,HttpServletRequest request)
			throws ErrorHandler, UserNotFoundException {
		return userService.updateUserProfile(updateUserProfileDto, session,request);
	}

	@PutMapping("/admin/{id}")
	public String updateUserRole(HttpSession session, @PathVariable String id, @RequestBody UpdateRole uRole)
			throws ErrorHandler, UserNotFoundException {
		return userService.updateUserRole(session, id, uRole);
	}

}
