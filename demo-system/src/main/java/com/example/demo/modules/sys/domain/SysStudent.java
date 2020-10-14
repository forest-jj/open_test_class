package com.example.demo.modules.sys.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

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

    /**
     * userId
     */
    private Integer userId;


    /**
     * 课程Id
     */
    private Integer classesId;

    /**
     * 课程名称
     */
    @Transient
    private String classesName;

    /**
     * 创建时间
     */
    @CreatedDate
    private LocalDateTime createTime;


    /**
     * 修改时间
     */
    @LastModifiedDate
    private LocalDateTime updateTime;
}
