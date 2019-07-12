package com.example.demo.entity;


import lombok.Data;

/**
 * @Author: 无双老师【云析学院】
 * @Date: 2019-07-12 21:21
 * @Description:
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
