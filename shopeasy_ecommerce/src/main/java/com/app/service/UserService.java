package com.app.service;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.app.dto.UpdateUserDTO;
import com.app.dto.UpdateUserProfileDto;
import com.app.dto.UserDTO;
import com.app.dto.UserResponseDto;

public interface UserService {

	UserResponseDto createUser(UserDTO user,HttpSession session, HttpServletResponse response) throws ErrorHandler;

	public UserResponseDto loginUser(AuthRequest loginDto, HttpServletResponse response, HttpSession session)
			throws UserNotFoundException;


	public String deleteUser(String id) throws UserNotFoundException;

	public UserResponseDto getSingleUser(String id,HttpSession session) throws UserNotFoundException, ErrorHandler;

	public List<UserDTO> findAllUser(String token, HttpSession session) throws AccessDeniedException,
			TokenExpiredException, MalFormedTokenException, ResourceNotFoundException, ErrorHandler;

	public String logout(HttpServletResponse res);

	public String forgotPassword(EmailDto emailDto) throws UserNotFoundException;

	public String resetPassword(String token, PasswordDto passwordDto, HttpSession session,
			HttpServletResponse response) throws UserNotFoundException, ErrorHandler;

	public UserResponseDto getUserDetails(HttpSession session) throws ErrorHandler;
	
	public UserResponseDto updatePassword(UpdatePasswordDto upDto, HttpSession session)
			throws ErrorHandler, UserNotFoundException;
	
	public String updateUserProfile(UpdateUserProfileDto updateUserProfileDto, HttpSession session,HttpServletRequest request)
			throws ErrorHandler, UserNotFoundException ;
	
	public String updateUserRole(HttpSession session, String id, UpdateRole uRole)
			throws ErrorHandler, UserNotFoundException;
}
