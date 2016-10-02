package org.oxerr.spring.security.wechat.samples.helloworld.service.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.oxerr.spring.security.wechat.samples.helloworld.WeChatProperties;
import org.oxerr.spring.security.wechat.web.AbstractSimpleRedirectWeChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxinmy.weixin4j.mp.api.OauthApi;

@Service
public class WeChatServiceImpl extends AbstractSimpleRedirectWeChatService {

	private final Logger log = LogManager.getLogger();

	private WeChatProperties weChatProperties;

	private OauthApi oauthApi;

	@Autowired
	public WeChatServiceImpl(WeChatProperties weChatProperties, OauthApi oauthApi) {
		this.weChatProperties = weChatProperties;
		this.oauthApi = oauthApi;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getAuthorizationURL(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String redirectUri = this.getRedirectUri(request);
		String state = null;
		String scope = "snsapi_userinfo";
		String url = oauthApi.getAuthorizeURL(redirectUri, state, scope);
		return url;
	}

	private String getRedirectUri(HttpServletRequest request) {
		final StringBuffer urlStringBuffer = request.getRequestURL();
		final String queryString = request.getQueryString();
		if (queryString != null) {
			urlStringBuffer.append("?").append(queryString);
		}
		final URI uri = URI.create(urlStringBuffer.toString());
		final String scheme = uri.getScheme();

		// WeChat requires default port.
		final int port = -1;

		URI redirectUri;
		try {
			redirectUri = new URI(
				scheme, uri.getUserInfo(),
				this.weChatProperties.getRedirectDomain(), port,
				uri.getPath(), uri.getQuery(), uri.getFragment()
			);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}

		final String redirectUriString = redirectUri.toString();
		log.debug("redirectUriString: {}", redirectUriString);
		return redirectUriString;
	}

}
