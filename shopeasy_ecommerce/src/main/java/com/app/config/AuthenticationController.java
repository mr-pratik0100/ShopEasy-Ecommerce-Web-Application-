//package com.app.config;
//
//import java.io.IOException;
//
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.DisabledException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.app.dto.AuthRequest;
//import com.app.dto.AuthResponse;
//import com.app.jwt.UserDetailsImpl;
//import com.app.util.JwtUtil;
//
//
//@RestController
//public class AuthenticationController {
//
//	@Autowired
//	private AuthenticationManager authenticationManager;
//	
//	@Autowired
//	private UserDetailsImpl userDetailsImpl;
//	
//	@Autowired
//	private JwtUtil jwtUtil;
//	
//	public AuthResponse createAuthToken(@RequestBody AuthRequest authRequest, HttpServletResponse response)throws BadCredentialsException, DisabledException,UsernameNotFoundException, IOException {
//		try {
//			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
//		}catch(BadCredentialsException e) {
//			throw new BadCredentialsException("Incorrect Username or Password");
//		}catch(DisabledException d) {
//			response.sendError(HttpServletResponse.SC_NOT_FOUND,"User is Not Created, Register a User first");
//			return null;
//		}
//		final UserDetails userDetails=userDetailsImpl.loadUserByUsername(authRequest.getEmail());
//		final String jwt=jwtUtil.generateToken(userDetails.getUsername());
//		return new AuthResponse(jwt);
//		
//	}
//}
