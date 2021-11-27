package org.oxerr.spring.security.wechat.web.authentication;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.oxerr.spring.security.wechat.authentication.WeChatAuthenticationToken;
import org.oxerr.spring.security.wechat.core.AuthDenyException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

public class WeChatAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	private final String AUTH_DENY_ATTRIBUTE_NAME = this.getClass().getName() + ".AuthDeny";
	private final WeChatService weChatService;

	public WeChatAuthenticationFilter(WeChatService weChatService) {
		super(new AntPathRequestMatcher("/login/wechat", "GET"));
		this.weChatService = weChatService;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		if (request.getSession().getAttribute(AUTH_DENY_ATTRIBUTE_NAME) == null
				&& isInWeChat(request)) {
			super.doFilter(request, response, chain);
		} else {
			chain.doFilter(request, response);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		final Authentication authentication;

		final String code = request.getParameter("code");

		if (code == null) {
			this.weChatService.redirectToAuthorize(request, response);
			authentication = null;
		} else {
			final WeChatAuthenticationToken authenticationToken = new WeChatAuthenticationToken(code);

			// delegate to the authentication provider
			try {
				authentication = this.getAuthenticationManager().authenticate(authenticationToken);
			} catch (AuthDenyException e) {
				request.getSession().setAttribute(AUTH_DENY_ATTRIBUTE_NAME, e);
				throw e;
			}
		}

		return authentication;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();

		Assert.notNull(this.weChatService, "A weChatService must be set.");
	}

	protected boolean isInWeChat(HttpServletRequest request) throws IOException {
		final String userAgent = request.getHeader("user-agent");
		return userAgent != null && userAgent.contains("MicroMessenger/");
	}

}
