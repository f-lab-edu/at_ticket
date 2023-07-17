package com.atticket.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@Profile("default")
@PropertySources({
	@PropertySource("classpath:config/.env.local") // env.properties 파일 소스 등록
})
public class LocalConfig {
}
