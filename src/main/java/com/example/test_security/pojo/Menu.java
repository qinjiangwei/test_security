package com.example.test_security.pojo;

import lombok.Data;

@Data
public class Menu {
    private Long id;
    private String url;
    private String name;
    private String icon;
    private Long parentId;
}
