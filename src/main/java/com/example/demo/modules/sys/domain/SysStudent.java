package com.example.demo.modules.sys.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


/**
 * 学生订阅课程管理
 */
@Entity
@Data
public class SysStudent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    @ManyToOne
    @JoinColumn(name = "userId")
    private SysUser sysUser;

    @Embedded
    @ManyToOne
    @JoinColumn(name = "classesId")
    private SysClass sysClass;
}
