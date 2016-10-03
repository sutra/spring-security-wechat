package org.oxerr.spring.security.wechat.web;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.oxerr.spring.security.wechat.core.WeChatMessageSource;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.util.FileCopyUtils;

public abstract class AbstractJavaScriptRedirectWeChatService extends
		AbstractSimpleRedirectWeChatService implements MessageSourceAware {

	protected MessageSourceAccessor messages = WeChatMessageSource.getAccessor();
	private final String template;

	public AbstractJavaScriptRedirectWeChatService() {
		try (Reader reader = new InputStreamReader(
			AbstractJavaScriptRedirectWeChatService.class
				.getResourceAsStream("loading.html"),
			StandardCharsets.UTF_8)) {
			template = FileCopyUtils.copyToString(reader);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messages = new MessageSourceAccessor(messageSource);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void redirectToAuthorize(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String redirectionPageHtml = generateRedirectionPageHtml(request, response);

		response.setContentType("text/html;charset=UTF-8");
		response.setContentLength(redirectionPageHtml.length());
		response.getWriter().write(redirectionPageHtml);
	}

	private String generateRedirectionPageHtml(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String url = this.getAuthorizationURL(request, response);
		String s = template.replaceFirst("#\\{loading\\}",
			messages.getMessage(
				"AbstractJavaScriptRedirectWeChatService.loading",
				"Loading..."));
		s = s.replaceFirst("\\$\\{url\\}", url);
		return s;
	}

}
