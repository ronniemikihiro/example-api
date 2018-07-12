package com.example.api.cors;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.example.api.config.property.ExampleApiProperty;

/**
 * Realiza um filter via servlet para permitir conexões externas.
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {

	private static final String OPTIONS = "OPTIONS";
	private static final String ORIGIN = "Origin";

	private static final String AC_ALLOW_ORIGIN = "Access-Control-Allow-Origin";

	private static final String AC_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";
	private static final String AC_ALLOW_CREDENTIALS_VALUE = "true";

	private static final String AC_ALLOW_METHODS = "Access-Control-Allow-Methods";
	private static final String AC_ALLOW_METHODS_VALUE = "POST, GET, DELETE, PUT, OPTIONS";

	private static final String AC_ALLOW_HEADERS = "Access-Control-Allow-Headers";
	private static final String AC_ALLOW_HEADERS_VALUE = "Authorization, Content-Type, Accept";

	private static final String AC_ALLOW_MAX_AGE = "Access-Control-Max-Age";
	private static final String AC_ALLOW_MAX_AGE_VALUE = "3600";

	@Autowired
	private ExampleApiProperty exampleApiProperty;

	/**
	 * Verifica se a origin que está tentando acessar um serviço via http é permitido.
	 *
	 * @param req
	 * @param resp
	 * @param chain
	 * @throws IOException
	 * @throws ServletException
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {

		final String originPermitida = exampleApiProperty.getOriginPermitida();
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		response.setHeader(AC_ALLOW_ORIGIN, originPermitida);
        response.setHeader(AC_ALLOW_CREDENTIALS, AC_ALLOW_CREDENTIALS_VALUE);
		
		if (OPTIONS.equals(request.getMethod()) && originPermitida.equals(request.getHeader(ORIGIN))) {
			response.setHeader(AC_ALLOW_METHODS, AC_ALLOW_METHODS_VALUE);
        	response.setHeader(AC_ALLOW_HEADERS, AC_ALLOW_HEADERS_VALUE);
        	response.setHeader(AC_ALLOW_MAX_AGE, AC_ALLOW_MAX_AGE_VALUE);
			
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			chain.doFilter(req, resp);
		}
		
	}

	/**
	 *
	 */
	@Override
	public void destroy() {
	}

	/**
	 *
	 * @param arg0
	 * @throws ServletException
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
