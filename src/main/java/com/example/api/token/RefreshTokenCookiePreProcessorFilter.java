package com.example.api.token;

import org.apache.catalina.util.ParameterMap;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

@Profile("oauth-security")
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RefreshTokenCookiePreProcessorFilter implements Filter {

	private static final String OAUTH_TOKEN = "/oauth/token";
	private static final String GRANT_TYPE = "grant_type";
	private static final String REFRESH_TOKEN = "refresh_token";
	private static final String REFRESHTOKEN = "refreshToken";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;

		if (OAUTH_TOKEN.equalsIgnoreCase(req.getRequestURI()) && REFRESH_TOKEN.equals(req.getParameter(GRANT_TYPE)) && req.getCookies() != null) {
			final Cookie refreshToken = Arrays.stream(req.getCookies()).filter(cookie -> REFRESHTOKEN.equals(cookie.getName())).findFirst().orElse(null);
			if(refreshToken != null) {
				req = new MyServletRequestWrapper(req, refreshToken.getValue());
			}
		}

		chain.doFilter(req, response);
	}

	@Override
	public void destroy() {}

	@Override
	public void init(FilterConfig arg0) throws ServletException {}

	static class MyServletRequestWrapper extends HttpServletRequestWrapper {

		private static final String REFRESH_TOKEN = "refresh_token";

		private String refreshToken;

		public MyServletRequestWrapper(HttpServletRequest request, String refreshToken) {
			super(request);
			this.refreshToken = refreshToken;
		}

		@Override
		public Map<String, String[]> getParameterMap() {
			final ParameterMap<String, String[]> map = new ParameterMap<>(getRequest().getParameterMap());
			map.put(REFRESH_TOKEN, new String[] { refreshToken });
			map.setLocked(true);
			return map;
		}
	}

}
