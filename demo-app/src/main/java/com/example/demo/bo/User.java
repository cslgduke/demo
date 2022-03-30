package com.example.demo.bo;


import lombok.Data;

import javax.persistence.*;

/**
 * @author i565244
 */
@Entity
@Table(name="tl_user")
@Data
public class User {

    @Id	//主键id
    @GeneratedValue(strategy=GenerationType.IDENTITY)//主键生成策略
    @Column(name="id")//数据库字段名
    private Integer id;

    @Column(name="no",insertable=false,updatable = false)
    private String no;

    @Column(name="name")
    private String name;

    @Column(name="age")
    private Integer age;

    @Column(name="address")
    private String address;
}
