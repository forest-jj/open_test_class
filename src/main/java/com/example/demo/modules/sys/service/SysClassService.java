package com.example.demo.modules.sys.service;

import com.example.demo.modules.sys.domain.SysClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SysClassService extends JpaRepository<SysClass, Integer>, JpaSpecificationExecutor {

    List<SysClass> findByCountGreaterThanAndClassesIdIsNotInOrderByCreateTime(Integer count,List<Integer> classesId);

    List<SysClass> findByCountGreaterThan(Integer count);

}
