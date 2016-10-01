package org.oxerr.spring.security.wechat.samples.helloworld;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class WeChatPropertiesValidator implements Validator {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean supports(Class<?> type) {
		return type == WeChatProperties.class;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate(Object o, Errors errors) {
		final WeChatProperties properties = (WeChatProperties) o;

		if (!StringUtils.hasText(properties.getAppId())) {
			errors.rejectValue("appId", null, "Missing appId");
		}

		if (!StringUtils.hasText(properties.getSecret())) {
			errors.rejectValue("secret", null, "Missing secret");
		}

		if (!StringUtils.hasText(properties.getRedirectDomain())) {
			errors.rejectValue("redirectDomain", null, "Missing redirectDomain");
		}
	}

}
