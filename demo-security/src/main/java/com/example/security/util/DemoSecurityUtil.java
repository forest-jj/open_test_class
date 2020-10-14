package com.example.security.util;


import com.alibaba.fastjson.JSON;
import com.example.common.exception.DemoBaseException;
import com.example.common.utils.R;
import com.example.security.DemoSecurityUser;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@UtilityClass
public class DemoSecurityUtil {

    public void writeJavaScript(R r, HttpServletResponse response) throws IOException {
        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.write(JSON.toJSONString(r));
        printWriter.flush();
    }

    /**
     * 获取Authentication
     */
    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }


    /**
     * 获取用户
     */
    public DemoSecurityUser getUser(){
        try {
            return (DemoSecurityUser) getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new DemoBaseException("登录状态过期", HttpStatus.UNAUTHORIZED.value());
        }
    }
}
