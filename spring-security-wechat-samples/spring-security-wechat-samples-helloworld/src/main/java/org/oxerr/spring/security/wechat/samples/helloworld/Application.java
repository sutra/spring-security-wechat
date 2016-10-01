package org.oxerr.spring.security.wechat.samples.helloworld;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.validation.Validator;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import com.foxinmy.weixin4j.model.WeixinAccount;
import com.foxinmy.weixin4j.mp.api.OauthApi;

@SpringBootApplication
@EnableGlobalMethodSecurity(jsr250Enabled = true)
public class Application {

	/**
	 * Writes the request URI (and optionally the query string) to the Commons Log.
	 *
	 * @return the filter registration bean for the {@link CommonsRequestLoggingFilter}.
	 */
	@Bean
	public FilterRegistrationBean commonsRequestLoggingFilterRegistrationBean() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		CommonsRequestLoggingFilter requestLoggingFilter = new CommonsRequestLoggingFilter();
		registrationBean.setFilter(requestLoggingFilter);
		registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return registrationBean;
	}

	@Bean
	@Autowired
	public OauthApi oauthApi(WeChatProperties weChatProperties) {
		WeixinAccount weixinAccount = new WeixinAccount(weChatProperties.getAppId(), weChatProperties.getSecret());
		return new OauthApi(weixinAccount);
	}

	@Bean
	public WeChatProperties weChatProperties() {
		return new WeChatProperties();
	}

	@Bean
	public Validator configurationPropertiesValidator() {
		return new WeChatPropertiesValidator();
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
