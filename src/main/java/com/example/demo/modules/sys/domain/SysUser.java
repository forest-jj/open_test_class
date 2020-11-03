package com.example.demo.modules.sys.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * @Classname DemoConstant
 * @Description 用户表
 * @Author Created by ZhangJian 346796040@qq.com
 * @Date 2020-10-10
 * @Version 1.0
 */
@Entity
@Data
@Table(name = "sysUser",uniqueConstraints = {@UniqueConstraint(name = "username",columnNames = "username")})
@SQLDelete(sql = "update sys_user set del_flag = '1' where user_Id = ?")
@Where(clause = "del_flag = 0")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SysUser  extends BasicDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;


    /**
     * 用户名
     */
    @Column(name = "username")
    private String username;

    /**
     * 密码
     */
    private String password;


    /**
     * 角色: 0:admin 1:teacher 2:student
     */
    @Column(nullable = false,length = 1)
    private String role;


    @Column(nullable = false,length = 1,columnDefinition = "char(1) NOT NULL DEFAULT '0'")
    private String delFlag = "0";

    @OneToMany(mappedBy = "sysUser",cascade = {CascadeType.REMOVE})
    private Set<SysStudent> sysStudents;

}
