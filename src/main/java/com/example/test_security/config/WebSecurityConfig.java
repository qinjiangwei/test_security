package com.example.test_security.config;

import com.example.test_security.md5.Md5PasswordEncoder;
import com.example.test_security.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //所有人都可以访问的权限
       http.authorizeRequests().antMatchers("/favicon.ico","/css/**","/common/**","/js/**","/images/**","/captcha.jpg","/login","/userLogin","/login-error").permitAll()
               //登录过后才能访问
               .anyRequest().authenticated()
               .and()
               .formLogin().loginPage("login").successForwardUrl("/index").failureForwardUrl("/login?error=1")
               .and()
               //权限拒绝页面
               .exceptionHandling().accessDeniedPage("403");
    }

    //自定义密码比较器
    @Bean
    public Md5PasswordEncoder passwordEncoder(){
        return new Md5PasswordEncoder();
    }

    //自定义UserDetailService授权
    @Bean
    public CustomUserDetailsService customUserDetailsService(){
     return new CustomUserDetailsService();
    }

    @Override
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
