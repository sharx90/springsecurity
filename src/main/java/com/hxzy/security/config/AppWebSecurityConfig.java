package com.hxzy.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // 开启全局的方法权限控制
public class AppWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailService myUserDetailService;

    @Autowired
    private MyPasswordEncoder myPasswordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        super.configure(http);

        // 设置登录页面
        http.formLogin()
                .loginPage("/index.jsp")
                .loginProcessingUrl("/doLogin") //登录映射, 默认 login
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/main.html"); // 登录成功路径 (重定向)

        http.authorizeRequests()
                // 设置指定的映射路径不认证
                .antMatchers("/layui/**","/index.jsp").permitAll()
//                .antMatchers("/level1/**").hasRole("学徒")
//                .antMatchers("/level2/1").hasAnyAuthority("ROLE_大师","太极拳")
////                .antMatchers("/level2/**").hasRole("大师")
//                .antMatchers("/level3/**").hasRole("宗师")
                // 剩余的请求都需要认证 (登录)
                .anyRequest().authenticated();

        // 当前没有权限是访问的页面地址
        http.exceptionHandling().accessDeniedPage("/unauth.html");

        // 开启记住我功能
        http.rememberMe(); // 登录表单需要添加 remember-me 参数

        // 添加注销
        http.logout().logoutSuccessUrl("/index.jsp"); //默认的退出映射路径 logout

        http.csrf().disable();// 禁用 csrf （不建议禁用）
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

//        auth.inMemoryAuthentication() //设置本地内存认证
//            // 设置用户及其密码和角色
//                // roles() : 添加角色     authorities(): 添加权限
//            .withUser("zhangsan").password("123456").roles("学徒","大师")
//            .and()
//            .withUser("lisi").password("123456").authorities("太极拳");

        // auth.jdbcAuthentication().authoritiesByUsernameQuery();  // 不推荐使用，需要使用SpringSecurity自定的数据表

        // 自定义认证 (连接数据库)

        // 使用自定义的加密规则
//        auth.userDetailsService(myUserDetailService).passwordEncoder(myPasswordEncoder);
        auth.userDetailsService(myUserDetailService).passwordEncoder(new BCryptPasswordEncoder());


    }
}
