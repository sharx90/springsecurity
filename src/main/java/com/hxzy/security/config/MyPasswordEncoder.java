package com.hxzy.security.config;

import com.hxzy.security.util.MD5Util;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MyPasswordEncoder implements PasswordEncoder {

    // 加密规则
    @Override
    public String encode(CharSequence charSequence) {
        String digest = MD5Util.digest(charSequence.toString());
        return digest;
    }


    /**
     * 比较表单提交的密码与后台密码
     * @param charSequence    表单密码
     * @param s               数据库查询的密码
     * @return
     */
    @Override
    public boolean matches(CharSequence charSequence, String s) {
        System.out.println(charSequence +"-----"+ s);
        return s.equals(MD5Util.digest(charSequence.toString()));
    }

}
