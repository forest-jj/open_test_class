package com.example.demo.modules.sys.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BasicDomain  implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 创建时间
     */
    @CreatedDate
    @Column(name = "create_time")
    private LocalDateTime createTime;


    /**
     * 修改时间
     */
    @LastModifiedDate
    @Column(name = "update_time")
    private LocalDateTime updateTime;
}
