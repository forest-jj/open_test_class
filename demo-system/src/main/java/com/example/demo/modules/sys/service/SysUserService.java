package com.example.demo.modules.sys.service;

import com.example.demo.modules.sys.domain.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SysUserService extends JpaRepository<SysUser, Integer> {

    @Query("select e from SysUser e where e.username = ?1")
    SysUser findSecurityUserByUser(String username);

    @Query("select e.role from SysUser e where e.userId = ?1")
    String findPermsByUserId(Integer userId);
}
