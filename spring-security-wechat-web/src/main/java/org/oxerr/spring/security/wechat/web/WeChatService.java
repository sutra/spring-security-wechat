package org.oxerr.spring.security.wechat.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface WeChatService {

	/**
	 * Redirect to WeChat server to authorize.
	 *
	 * @param request the HTTP request.
	 * @param response the HTTP response.
	 * @throws ServletException indicates servlet exception.
	 * @throws IOException indicates I/O exception.
	 */
	void redirectToAuthorize(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException;

}
