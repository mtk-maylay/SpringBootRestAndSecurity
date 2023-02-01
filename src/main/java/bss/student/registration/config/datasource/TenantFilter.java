package bss.student.registration.config.datasource;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import bss.student.registration.security.JwtTokenProvider;

@Component
@Order(1)
public class TenantFilter implements Filter {

	private JwtTokenProvider jwtTokenProvider;

	public TenantFilter(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		// String tenantName = req.getHeader("X_TenantID");

		String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
		if (token == null) {
			TenantContext.setCurrentTenant("");

		} else if (token != null && jwtTokenProvider.validateToken(token)) {

			String tenant = jwtTokenProvider.getAudience(token);

			TenantContext.setCurrentTenant(tenant);
		}

		try

		{
			chain.doFilter(request, response);
		} finally {
			TenantContext.setCurrentTenant("");
		}
	}

}
