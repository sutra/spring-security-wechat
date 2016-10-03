package org.oxerr.spring.security.wechat.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractSimpleRedirectWeChatService
		implements WeChatService {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void redirectToAuthorize(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String url = this.getAuthorizationURL(request, response);
		response.sendRedirect(url);
	}

	public abstract String getAuthorizationURL(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException;

}
