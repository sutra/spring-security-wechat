package org.oxerr.spring.security.wechat.samples.helloworld;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = WeChatProperties.PREFIX)
public class WeChatProperties {

	static final String PREFIX = "wechat";

	private String appId, secret;
	private String redirectDomain;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getRedirectDomain() {
		return redirectDomain;
	}

	public void setRedirectDomain(String redirectDomain) {
		this.redirectDomain = redirectDomain;
	}

}
