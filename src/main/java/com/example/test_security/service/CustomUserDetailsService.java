package com.example.test_security.service;

import com.example.test_security.pojo.Menu;
import com.example.test_security.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义UserDetailsService实现用户的授权和认证
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        //认证账号
        User user = userService.loadUserByUsername(s);
        if(user == null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        //开始授权
        List<Menu> menus = menuService.getMenusById(user.getId());
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        menus.forEach(menu ->{
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(menu.getUrl());
            grantedAuthorities.add(authority);
        });
        user.setAuthorities(grantedAuthorities);
        return user;
    }
}
