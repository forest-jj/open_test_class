package com.example.demo.modules.security.handle;

import com.example.demo.security.DemoSecurityUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description 登录成功处理器
 */
@Component
public class DemoAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Object principal = authentication.getPrincipal();
        if (principal instanceof DemoSecurityUser) {
            //存储认证信息
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

    }
}
