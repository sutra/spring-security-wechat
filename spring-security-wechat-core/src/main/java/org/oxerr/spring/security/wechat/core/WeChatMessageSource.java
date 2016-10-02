package org.oxerr.spring.security.wechat.core;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;

public class WeChatMessageSource extends ResourceBundleMessageSource {

	public WeChatMessageSource() {
		setBasename("org.oxerr.spring.security.wechat.messages");
	}

	public static MessageSourceAccessor getAccessor() {
		return new MessageSourceAccessor(new WeChatMessageSource());
	}

}
