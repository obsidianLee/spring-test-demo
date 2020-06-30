package com.test.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Lixueyuan
 * @date 2019/11/19 16:44
 */
@Entity
@Table(name = "class_one")
@Data
public class ClassOne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer studentNo;
    private String name;
    private Integer sex;
}
