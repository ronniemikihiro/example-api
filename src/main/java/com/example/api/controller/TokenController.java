package com.example.api.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.config.property.ExampleApiProperty;

/**
 * Controller que manipula os cookies.
 */
@Profile("oauth-security")
@RestController
@RequestMapping("/tokens")
public class TokenController {

	@Autowired
	private ExampleApiProperty exampleApiProperty;

	/**
	 * Mata o cookie ativo.
	 *
	 * @param req
	 * @param resp
	 */
	@DeleteMapping(value = "/revoke")
	public void revoke(final HttpServletRequest req, final HttpServletResponse resp) {
		final Cookie cookie = new Cookie("refreshToken", null);
		cookie.setHttpOnly(true);
		cookie.setSecure(exampleApiProperty.getSeguranca().isEnableHttps());
		cookie.setPath(req.getContextPath() + "/oauth/token");
		cookie.setMaxAge(0);

		resp.addCookie(cookie);
		resp.setStatus(HttpStatus.NO_CONTENT.value());
	}

}
