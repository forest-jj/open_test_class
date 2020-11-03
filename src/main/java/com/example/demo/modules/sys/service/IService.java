package com.example.demo.modules.sys.service;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface IService {
    List<?> findByCondition(JpaSpecificationExecutor jpaSpecificationExecutor,Object entity);
}
