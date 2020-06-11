package com.hxzy.security.test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPasswrdTest {


    public static void main(String[] args) {


        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String encode = encoder.encode("123456");

        System.out.println(encode);
        //$2a$10$x8PRl.kqdpNOC.1/hVDwVugi5SPU1d8N/kUJTpPiXrAefWgk/bxlm
        //$2a$10$CSLMqUa9iij0/Q.VbZ2D5.KzHjmzGpUE1GjwZthvzaazT42ViNXAa
        //$2a$10$UGjk49v.iWOSaOlSdCEFWO9oy84Fi.Nrl5IMqNgezRnMUlUZHzfrC

    }

}
