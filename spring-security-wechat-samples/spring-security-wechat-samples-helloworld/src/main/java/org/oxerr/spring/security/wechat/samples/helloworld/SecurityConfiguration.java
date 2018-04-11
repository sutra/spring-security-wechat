package org.oxerr.spring.security.wechat.samples.helloworld;

import org.oxerr.spring.security.wechat.config.annotation.web.configurers.WeChatLoginConfigurer;
import org.oxerr.spring.security.wechat.core.WeChatUserDetailsService;
import org.oxerr.spring.security.wechat.web.WeChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfiguration {

	@Configuration
	public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

		@Autowired
		private WeChatService weChatService;

		@Autowired
		private WeChatUserDetailsService weChatUserDetailsService;

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
				.authorizeRequests()
					.anyRequest().permitAll()
					.and()
				.formLogin().permitAll()
					.and()
				.apply(new WeChatLoginConfigurer<>(weChatService, weChatUserDetailsService)).loginProcessingUrl("/login").permitAll();
		}

	}

}
