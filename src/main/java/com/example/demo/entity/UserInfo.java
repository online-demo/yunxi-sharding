package com.example.demo.entity;

import lombok.Data;

/**
 * 用户信息
 */
@Data
public class UserInfo {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户内容
     */
    private String account;

    /**
     * 用户密码
     */
    private String password;
}
