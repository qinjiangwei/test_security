package com.example.test_security.pojo;

import lombok.Data;

import java.util.List;

@Data
public class R {
    private String retCode;
    private String retMsg;
    private Object retBean;
    private List<Object> retBeans;
}
