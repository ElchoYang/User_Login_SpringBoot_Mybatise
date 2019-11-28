package com.elcho.springboot.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SecurityProperties.class)// 使SecurityProperties生效
public class SecurityPropertiesConfig {

}