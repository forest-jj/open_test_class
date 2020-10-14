package com.example.demo.modules.security.filter;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.utils.DemoUtils;
import com.example.demo.modules.security.util.JwtUtil;
import com.example.demo.modules.sys.service.SysUserService;
import com.example.security.DemoSecurityUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * @Description token过滤器来验证token有效性
 */
@Slf4j
@Component
public class DemoJwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private SysUserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        DemoSecurityUser securityUser = jwtUtil.getUserFromToken(request);
        if (ObjectUtil.isNotNull(securityUser)){
            String permissions = DemoUtils.getUserAuthorities(userService.findPermsByUserId(securityUser.getUserId())) + "";
            Collection<? extends GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(permissions);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(securityUser, null, authorities);
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        chain.doFilter(request, response);
    }
}
