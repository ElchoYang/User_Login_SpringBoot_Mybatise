package com.elcho.springboot.passcode;

import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderImpl implements PasswordEncoder {

    @Override
    public String encode(CharSequence charSequence) {
        //return null;
        //自定义
        return charSequence.toString();
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        //return false;
        return s.equals(charSequence.toString());
    }
}
