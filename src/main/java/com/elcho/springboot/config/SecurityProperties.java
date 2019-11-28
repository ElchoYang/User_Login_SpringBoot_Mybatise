package com.elcho.springboot.config;

import com.elcho.springboot.entity.LoginType;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "xh.security")
public class SecurityProperties {

    private LoginType loginType = LoginType.JSON; // default json

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

}
