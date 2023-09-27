package com.app.jwt;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.app.dto.AuthResponse;

public class SaveCookie {

	public static AuthResponse sendToken(String token, HttpServletResponse response) {
		Cookie cookie = new Cookie("tokenjwt", token);
		cookie.setMaxAge(3 * 24 * 60 * 60);
		cookie.setHttpOnly(true);
		response.addCookie(cookie);
		return new AuthResponse(token);
	}

}
