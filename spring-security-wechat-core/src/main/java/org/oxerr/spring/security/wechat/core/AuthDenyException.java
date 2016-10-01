package org.oxerr.spring.security.wechat.core;

import org.springframework.security.core.AuthenticationException;

public class AuthDenyException extends AuthenticationException {

	private static final long serialVersionUID = 2016100101L;

	public AuthDenyException(String msg) {
		super(msg);
	}

}
