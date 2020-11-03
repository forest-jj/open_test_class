package com.example.demo.modules.security.handle;

import com.example.demo.common.utils.R;
import com.example.demo.security.util.DemoSecurityUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败处理器
 */
@Component
public class DemoAuthenticationFailureHandler  implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        String message;

        if (exception instanceof AuthenticationException) {
            message = exception.getMessage();
        } else {
            message = "认证失败，请联系网站管理员！";
        }
        response.setContentType("application/json;charset=utf-8");
        DemoSecurityUtil.writeJavaScript(R.error(message), response);
    }
}
