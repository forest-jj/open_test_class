package com.example.demo.common.utils;

import com.example.demo.common.constant.DemoConstant;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;

@UtilityClass
public class DemoUtils {

    /**
     * 生成BCryptPasswordEncoder密码
     *
     * @param rawPass
     * @return
     */
    public String encode(String rawPass) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(rawPass);
    }

    /**
     * 封装 根据用户Id获取权限
     *
     * @param role
     * @return
     */
    public Collection<? extends GrantedAuthority> getUserAuthorities (String role){
        // 获取用户拥有的角色
        String permissions = "ROLE_";

        switch (role) {
            case "1":
                permissions += DemoConstant.DEMO_TEACHER;
                break;
            case "2":
                permissions += DemoConstant.DEMO_STUDENT;
                break;
            default:
                permissions += DemoConstant.DEMO_ADMINISTRATOR;
        }


        // 角色集合
        Collection<? extends GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(permissions);
        return authorities;
    }
}
