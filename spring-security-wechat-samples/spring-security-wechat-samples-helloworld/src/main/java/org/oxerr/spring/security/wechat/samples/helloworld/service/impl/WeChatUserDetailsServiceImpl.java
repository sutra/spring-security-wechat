package org.oxerr.spring.security.wechat.samples.helloworld.service.impl;

import org.oxerr.spring.security.wechat.core.userdetails.WeChatUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.mp.api.OauthApi;
import com.foxinmy.weixin4j.mp.model.OauthToken;
import com.foxinmy.weixin4j.mp.model.User;

@Service
public class WeChatUserDetailsServiceImpl implements WeChatUserDetailsService {

	private OauthApi oauthApi;

	@Autowired
	public WeChatUserDetailsServiceImpl(OauthApi oauthApi) {
		this.oauthApi = oauthApi;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserDetails loadUserByCode(String code)
			throws AuthenticationException {
		try {
			OauthToken oauthToken = oauthApi.getAuthorizationToken(code);
			User user = oauthApi.getAuthorizationUser(oauthToken);
			return new WeChatUserDetails(user);
		} catch (WeixinException e) {
			throw new BadCredentialsException(e.getMessage(), e);
		}
	}

}
