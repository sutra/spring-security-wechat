package org.oxerr.spring.security.wechat.core.userdetails;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

public interface WeChatUserDetailsService {

	UserDetails loadUserByCode(String code) throws AuthenticationException;

}
