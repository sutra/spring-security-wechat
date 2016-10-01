package org.oxerr.spring.security.wechat.core;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class WeChatAuthenticationToken
		extends AbstractAuthenticationToken {

	private static final long serialVersionUID = 2016100101L;

	private final UserDetails userDetails;
	private String code;

	public WeChatAuthenticationToken(String code) {
		super(null);
		this.userDetails = null;
		this.code = code;

		super.setAuthenticated(false);
	}

	public WeChatAuthenticationToken(UserDetails userDetails) {
		super(userDetails.getAuthorities());
		this.userDetails = userDetails;

		super.setAuthenticated(true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCredentials() {
		return code;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getPrincipal() {
		return userDetails;
	}

}
