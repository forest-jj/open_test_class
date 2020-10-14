package com.example.demo.modules.sys.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 课程管理
 */
@Entity
@Data
@Accessors(chain = true)
public class SysClass  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer classesId;

    /**
     * 课程名称
     */
    private String classesName;


    /**
     * 课程数量
     */
    private Integer count;


    @Version
    private Integer version;

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
