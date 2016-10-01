package org.oxerr.spring.security.wechat.samples.helloworld.service.impl;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.foxinmy.weixin4j.mp.model.User;

public class WeChatUserDetails implements UserDetails {

	private static final long serialVersionUID = 2016100101L;

	private final User user;

	public WeChatUserDetails(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new GrantedAuthority() {

			private static final long serialVersionUID = 2016100101L;

			@Override
			public String getAuthority() {
				return "ROLE_USER";
			}

		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getPassword() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getUsername() {
		return "username of " + this.getUser().getOpenId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.getUser().toString();
	}

}
