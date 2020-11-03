package com.example.demo.modules.sys.domain;

import com.example.demo.common.annotation.GreaterThan;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Version;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * 课程管理
 */
@Entity
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SysClass  extends BasicDomain implements Serializable {

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
    @GreaterThan
    private Integer count;


    @Version
    private Integer version;

    @JsonBackReference
    @OneToMany(mappedBy = "sysClass",cascade = {CascadeType.REMOVE})
    private Set<SysStudent> sysStudent;

}
