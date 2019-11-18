package com.elcho.springboot;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


public class UserLoginApplicationInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        //
        System.out.print("---- 基于web 方式启动SpringBoot应用 -----");

        return builder.sources(UserLoginSpringBootApplication.class);
    }
}
