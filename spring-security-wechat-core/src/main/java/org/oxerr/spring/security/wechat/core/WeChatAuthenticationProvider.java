package org.oxerr.spring.security.wechat.core;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

public class WeChatAuthenticationProvider implements AuthenticationProvider,
		InitializingBean, MessageSourceAware {

	protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
	private final WeChatUserDetailsService weChatUserDetailsService;

	public WeChatAuthenticationProvider(WeChatUserDetailsService weChatUserDetailsService) {
		this.weChatUserDetailsService = weChatUserDetailsService;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Authentication authenticate(final Authentication authentication)
			throws AuthenticationException {
		final WeChatAuthenticationToken authenticationToken = (WeChatAuthenticationToken) authentication;

		final String code = authenticationToken.getCredentials();

		if (code.equals("authdeny")) {
			throw new AuthDenyException(messages.getMessage(
					"WeChatAuthenticationProvider.authdeny",
					"User denied."));

		}

		final UserDetails userDetails = weChatUserDetailsService.loadUserByCode(code);
		return new WeChatAuthenticationToken(userDetails);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		return WeChatAuthenticationToken.class.isAssignableFrom(authentication);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(this.weChatUserDetailsService, "A weChatUserDetailsService must be set.");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messages = new MessageSourceAccessor(messageSource);
	}

}
