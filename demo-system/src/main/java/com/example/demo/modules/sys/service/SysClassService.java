package com.example.demo.modules.sys.service;

import com.example.demo.modules.sys.domain.SysClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SysClassService extends JpaRepository<SysClass, Integer> {

    @Override
    <S extends SysClass> S saveAndFlush(S s);

    @Override
    void delete(SysClass sysClass);

    List<SysClass> findByCountGreaterThan(Integer count);
}
