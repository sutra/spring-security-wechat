package org.oxerr.spring.security.wechat.config.annotation.web.configurers;

import org.oxerr.spring.security.wechat.authentication.WeChatAuthenticationProvider;
import org.oxerr.spring.security.wechat.core.userdetails.WeChatUserDetailsService;
import org.oxerr.spring.security.wechat.web.authentication.WeChatAuthenticationFilter;
import org.oxerr.spring.security.wechat.web.authentication.WeChatService;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public final class WeChatLoginConfigurer<H extends HttpSecurityBuilder<H>>
		extends
		AbstractAuthenticationFilterConfigurer<H, WeChatLoginConfigurer<H>,
		WeChatAuthenticationFilter> {

	private final WeChatUserDetailsService weChatUserDetailsService;

	public WeChatLoginConfigurer(
		WeChatService weChatService,
		WeChatUserDetailsService weChatUserDetailsService
	) {
		super(new WeChatAuthenticationFilter(weChatService), "/login/wechat");
		this.weChatUserDetailsService = weChatUserDetailsService;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void configure(H http) throws Exception {

		// Make sure the filter be registered in
		// org.springframework.security.config.annotation.web.builders.FilterComparator
		http.addFilterAfter(getAuthenticationFilter(), LogoutFilter.class);

		super.configure(http);
	}

	@Override
	public WeChatLoginConfigurer<H> loginPage(String loginPage) {
		return super.loginPage(loginPage);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(H http) throws Exception {
		super.init(http);

		WeChatAuthenticationProvider authenticationProvider = new WeChatAuthenticationProvider(weChatUserDetailsService);
		postProcess(authenticationProvider);
		http.authenticationProvider(authenticationProvider);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected RequestMatcher createLoginProcessingUrlMatcher(
			String loginProcessingUrl) {
		return new AntPathRequestMatcher(loginProcessingUrl, "GET");
	}

}
