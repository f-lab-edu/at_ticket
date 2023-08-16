package com.atticket.common.secretconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:secret-config/.env")
public class SecretConfig {
}
