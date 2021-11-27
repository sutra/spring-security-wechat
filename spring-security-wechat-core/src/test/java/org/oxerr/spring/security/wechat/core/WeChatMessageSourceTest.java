package org.oxerr.spring.security.wechat.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Locale;

import org.junit.jupiter.api.Test;

class WeChatMessageSourceTest {

	@Test
	void testGetAccessor() {
		assertEquals("User denied.",
		WeChatMessageSource.getAccessor()
			.getMessage("WeChatAuthenticationProvider.authdeny", Locale.ENGLISH)
		);
	}

}
