package com.example.demo.modules.security;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.constant.DemoConstant;
import com.example.common.utils.DemoUtils;
import com.example.demo.modules.sys.domain.SysUser;
import com.example.demo.modules.sys.service.SysUserService;
import com.example.security.DemoSecurityUser;
import com.example.security.LoginType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Collection;


@Slf4j
@Repository
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        SysUser user = userService.findSecurityUserByUser(username);
        if (ObjectUtil.isNull(user)) {
            log.info("登录用户：" + username + " 不存在.");
            throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
        }

        LoginType loginType = getLoginType(user.getRole());
        Collection<? extends GrantedAuthority> authorities = DemoUtils.getUserAuthorities(user.getRole());

        return new DemoSecurityUser(loginType, user.getUserId(), username, user.getPassword(), authorities);
    }

    /**
     * 封装 根据用户Id获取类型
     *
     * @param role
     * @return
     */
    private LoginType getLoginType(String role) {
        switch (role) {
            case "0":
                return LoginType.ADMIN;
            case "1":
                return LoginType.TEACHER;
            case "2":
                return LoginType.STUDENT;
            default:
                return null;
        }

    }

    }
