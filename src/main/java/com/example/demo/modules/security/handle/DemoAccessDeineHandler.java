package com.example.demo.modules.security.handle;

import cn.hutool.http.HttpStatus;
import com.example.demo.common.utils.R;
import com.example.demo.security.util.DemoSecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description 用来解决匿名用户访问无权限资源时的异常
 */
@Slf4j
public class DemoAccessDeineHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        log.error("请求访问: " + httpServletRequest.getRequestURI() + " 接口， 没有访问权限");
        DemoSecurityUtil.writeJavaScript(R.error(HttpStatus.HTTP_UNAUTHORIZED, "请求访问:" + httpServletRequest.getRequestURI() + "接口,没有访问权限"), httpServletResponse);
    }
}
