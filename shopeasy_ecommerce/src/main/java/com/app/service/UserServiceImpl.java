package com.app.service;

import java.nio.file.AccessDeniedException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.collections.User;
import com.app.custom_exceptions.ErrorHandler;
import com.app.custom_exceptions.MalFormedTokenException;
import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.custom_exceptions.TokenExpiredException;
import com.app.custom_exceptions.UserNotFoundException;
import com.app.dto.AuthRequest;
import com.app.dto.EmailDto;
import com.app.dto.PasswordDto;
import com.app.dto.UpdatePasswordDto;
import com.app.dto.UpdateRole;
import com.app.dto.UpdateUserProfileDto;
import com.app.dto.UserDTO;
import com.app.dto.UserResponseDto;
import com.app.jwt.SaveCookie;
import com.app.repository.UserRepository;
import com.app.util.JwtUtil;
import com.cloudinary.Cloudinary;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private JavaMailSender javaMailSender;
	
	

	// send user also in response
	@Override
	public UserResponseDto loginUser(AuthRequest loginDto, HttpServletResponse response, HttpSession session)
			throws UserNotFoundException {
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

		if (loginDto.getEmail() == null || loginDto.getPassword() == null) {
			throw new UserNotFoundException("Please Enter Email or Password");
		}

		User user = userRepo.findByEmail(loginDto.getEmail());
		if (user != null) {

			if (bcrypt.matches(loginDto.getPassword(), user.getPassword())) {
				final String jwt = jwtUtil.generateToken(user.getId());
//				Optional<User> opUser = userRepo.findById(user.getId());
				session.setAttribute("user", user);
				 SaveCookie.sendToken(jwt, response);
				 UserResponseDto userDto=mapper.map(user, UserResponseDto.class);
				 return userDto;

			} else {
				throw new UserNotFoundException("Invalid Credentials");
			}
		}
		throw new UserNotFoundException("Invalid email or password");
	}

	@Override
	public UserResponseDto createUser(UserDTO userDto,HttpSession session, HttpServletResponse response) throws ErrorHandler {

		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
	
		User duplicateUser = userRepo.findByEmail(userDto.getEmail());
		if (duplicateUser != null) {
			throw new ErrorHandler("User alredy exists");
		}
		
		User user = mapper.map(userDto, User.class);
		String encryptedPwd = bcrypt.encode(user.getPassword());
		user.setPassword(encryptedPwd);
				
		final String jwt = jwtUtil.generateToken(user.getId());
		session.setAttribute("user", user);
		 SaveCookie.sendToken(jwt, response);
		userRepo.save(user);
		 UserResponseDto u=mapper.map(user, UserResponseDto.class);


		return u;
	}

	// ADMIN
	@Override
	public String deleteUser(String id) throws UserNotFoundException {
		User user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User Not Found"));
		userRepo.deleteById(id);
		return "User deleted successfully";

	}

	// ADMIN
	@Override
	public UserResponseDto getSingleUser(String id, HttpSession session) throws UserNotFoundException, ErrorHandler {

		User storedUser = (User) session.getAttribute("user");

		if (storedUser == null) {
			throw new ErrorHandler("Please Login first");
		}

		// Authorize user("Admin")
		String userRole = storedUser.getRole();
		if (userRole.equals("Admin")) {

		} else {
			throw new ErrorHandler(storedUser + " is not allowed to access this resource");
		}

		User user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User Not Found"));

		if (user == null) {
			throw new UserNotFoundException("Invalid user id");
		}

		UserResponseDto userResponseDto = mapper.map(user, UserResponseDto.class);

		return userResponseDto;
	}

//	ADMIN
	@Override
	public List<UserDTO> findAllUser(String tokenjwt, HttpSession session) throws AccessDeniedException,
			TokenExpiredException, MalFormedTokenException, ResourceNotFoundException, ErrorHandler {

		// Authenticate user
		if (tokenjwt == null) {
			throw new ResourceNotFoundException("Please login to access this resources");
		}

		User storedUser = (User) session.getAttribute("user");

		if (storedUser == null) {
			throw new ErrorHandler("Please Login first");
		}

		// Authorize user("Admin")
		String userRole = storedUser.getRole();
		if (userRole.equals("Admin")) {

		} else {
			throw new ErrorHandler(storedUser + " is not allowed to access this resource");
		}

		List<UserDTO> userDto = new ArrayList<>();
		List<User> user = userRepo.findAll();
		user.forEach(i -> {
			UserDTO u = mapper.map(i, UserDTO.class);
			userDto.add(u);
		});

		return userDto;
	}

	@Override
	public String logout(HttpServletResponse res) {
		Cookie cookie = new Cookie("tokenjwt", null);
		cookie.setMaxAge(0);
		res.addCookie(cookie);
		return "Logged out Successfully";
	}

	@Override
	public String forgotPassword(EmailDto emailDto) throws UserNotFoundException {

		User user = userRepo.findByEmail(emailDto.getEmail());

		System.out.println("User name " + user.getName());
		if (user == null)
			throw new UserNotFoundException("Invalid email");
		int expiresIn = 15;
		StringBuilder tokenBuilder = new StringBuilder();
		try {
			SecureRandom secureRandom = SecureRandom.getInstanceStrong();
			byte[] randomBytes = new byte[10];
			secureRandom.nextBytes(randomBytes);

			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			byte[] hashBytes = messageDigest.digest(randomBytes);

			for (byte hashByte : hashBytes) {
				tokenBuilder.append(String.format("%02x", hashByte));
			}

			LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(expiresIn);
			long expirationTimestamp = expirationTime.toEpochSecond(ZoneOffset.UTC);

			tokenBuilder.append("_").append(expirationTimestamp);

			user.setResetPasswordToken(tokenBuilder.toString());
			user.setResetPasswordExpire(expirationTime);
			userRepo.save(user);

			tokenBuilder.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Error generating reset password token", e);
		}
		try {
			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
			simpleMailMessage.setTo(emailDto.getEmail());
			simpleMailMessage.setSubject("Ecommerce Password Recovery");
			String resetPasswordUrl = "http://localhost:8080/user/password/reset/" + tokenBuilder.toString();
			String emailContent = "Your Password reset token is:<br><br><a href='" + resetPasswordUrl + "'>"
					+ "</a><br><br>If you have not requested this, please ignore it.";
			simpleMailMessage.setText(emailContent);

			javaMailSender.send(simpleMailMessage);
			return "Email successfully sent to " + emailDto.getEmail();
		} catch (Exception e) {
			user.setResetPasswordToken("");
			user.setResetPasswordExpire(LocalDateTime.now());
			userRepo.save(user);
			throw new RuntimeException("enable to send email please try again");
		}
	}

	@Override
	public String resetPassword(String token, PasswordDto passwordDto, HttpSession session,
			HttpServletResponse response) throws UserNotFoundException, ErrorHandler {
		System.out.println("In reset Password");
		if (passwordDto.getPassword() == null || passwordDto.getConfirmedPassword() == null) {
			throw new ErrorHandler("Please Insert Password ");
		}
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		StringBuilder hashedTokenBuilder = new StringBuilder();
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			byte[] hashBytes = messageDigest.digest(token.getBytes());

			for (byte hashByte : hashBytes) {
				hashedTokenBuilder.append(String.format("%02x", hashByte));
			}

			hashedTokenBuilder.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Error hashing token", e);
		}
		User user = userRepo.findByResetPasswordToken(token);
		if (user == null) {
			throw new UserNotFoundException("Reset Password token is invalid it has been expired");
		}
		if (passwordDto.getPassword().equals(passwordDto.getConfirmedPassword())) {

			String encryptedPwd = bcrypt.encode(passwordDto.getPassword());
			user.setPassword(encryptedPwd);
			user.setResetPasswordToken("");
			user.setResetPasswordExpire(LocalDateTime.now());

			userRepo.save(user);
			final String jwt = jwtUtil.generateToken(user.getId());
			System.out.println("login user token= " + jwt);
			Optional<User> opUser = userRepo.findById(user.getId());
			session.setAttribute("user", opUser.get());
			SaveCookie.sendToken(jwt, response);
			return "Password Chnaged Successfully";
		}
		throw new UserNotFoundException("Password Doesn't match");

	}

	@Override
	public UserResponseDto getUserDetails(HttpSession session) throws ErrorHandler {
		User storedUser = (User) session.getAttribute("user");
		if (storedUser == null) {
			throw new ErrorHandler("Login first");
		}
		UserResponseDto userResponse = mapper.map(storedUser, UserResponseDto.class);
		return userResponse;
	}

	@Override
	public UserResponseDto updatePassword(UpdatePasswordDto upDto, HttpSession session)
			throws ErrorHandler, UserNotFoundException {
//		User storedUser = (User) session.getAttribute("user");
		User storedUser=userRepo.findByEmail(upDto.getEmail());
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
//		if (storedUser == null) {
//			throw new ErrorHandler("Please Login first");
//		}
		Optional<User> u = userRepo.findById(storedUser.getId());
		User user = u.get();
		if (user == null) {
			throw new UserNotFoundException("User Not found");
		}

		if (bcrypt.matches(upDto.getOldPassword(), user.getPassword())) {
			if (upDto.getNewPassword().equals(upDto.getConfirmedPassword())) {
				String encryptedPwd = bcrypt.encode(upDto.getNewPassword());
				user.setPassword(encryptedPwd);
				userRepo.save(user);
			} else {
				throw new ErrorHandler("New Password doesn't match with confirmed Password");
			}
		} else {
			throw new ErrorHandler("Password doesn't match with old password");
		}
		UserResponseDto userResponse = mapper.map(storedUser, UserResponseDto.class);
		return userResponse;

	}

	@Override
	public String updateUserProfile(UpdateUserProfileDto updateUserProfileDto, HttpSession session,HttpServletRequest request)
			throws ErrorHandler, UserNotFoundException {
//		User storedUser = (User) session.getAttribute("user");

		
//		Cookie[] cookies=request.getCookies();
//		String cookieValue = null;
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//            	System.out.println("cookieValue=== "+ cookieValue);
//                if ("userr".equals(cookie.getName())) {
//                    cookieValue = cookie.getValue();
//                    break;
//                }
//            }
//        }
        
//        if (cookieValue != null) {
//            try {
//            	tUser = mapper.map(cookieValue, User.class);
//               
//            } catch (Exception e) {
//                // Handle JSON parsing exception
//            }
//        }
		
//		if (storedUser == null) {
//			throw new ErrorHandler("Please Login first");
//		}
//		Optional<User> u = userRepo.findById(storedUser.getId());
//		Optional<User> opUser = userRepo.findById(updateUserProfileDto.g);
        User user=userRepo.findByEmail(updateUserProfileDto.getEmail());
//		User user = opUser.get();
		if (user == null) {
			throw new UserNotFoundException("User Not found");
		}

		user.setName(updateUserProfileDto.getName());
		user.setEmail(updateUserProfileDto.getEmail());
//		user.setAvatar(updateUserProfileDto.getAvatar());
		userRepo.save(user);
		return "success";
	}

	@Override
	public String updateUserRole(HttpSession session, String id, UpdateRole uRole)
			throws ErrorHandler, UserNotFoundException {
		User storedUser = (User) session.getAttribute("user");

		if (storedUser == null) {
			throw new ErrorHandler("Please Login first");
		}

		// Authorize user("Admin")
		String userRole = storedUser.getRole();
		if (userRole.equals("Admin")) {

		} else {
			throw new ErrorHandler(storedUser + " is not allowed to access this resource");
		}

		Optional<User> updateUser = userRepo.findById(id);
		if (updateUser == null) {
			throw new UserNotFoundException("Invalid user id");
		}
		User user = updateUser.get();
		user.setRole(uRole.getRole());
		userRepo.save(user);
		return "User Role Updated Successsfully";
	}

}
