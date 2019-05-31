package com.example.test_security.md5;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 自定义密比较器，密码没有加密
 */
public class Md5PasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        return charSequence.toString();
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return charSequence.equals(s);
    }
}
