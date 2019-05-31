package com.example.test_security.service;

import com.example.test_security.pojo.Menu;

import java.util.List;

public interface MenuService {
    List<Menu> getMenusById(Long id);
}
