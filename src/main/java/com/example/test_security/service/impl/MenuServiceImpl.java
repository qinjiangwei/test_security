package com.example.test_security.service.impl;

import com.example.test_security.pojo.Menu;
import com.example.test_security.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {


    @Override
    public List<Menu> getMenusById(Long id) {
        return null;
    }
}
