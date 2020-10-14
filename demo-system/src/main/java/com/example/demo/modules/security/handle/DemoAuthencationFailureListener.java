package com.example.demo.modules.security.handle;

import com.example.common.exception.DemoBaseException;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.*;
import org.springframework.stereotype.Component;

/**
 * @Description  用户登录失败监听器事件
 */

@Component
public class DemoAuthencationFailureListener implements ApplicationListener<AbstractAuthenticationFailureEvent> {

    @Override
    public void onApplicationEvent(AbstractAuthenticationFailureEvent event) {
        if (event instanceof AuthenticationFailureBadCredentialsEvent) {
            //提供的凭据是错误的，用户名或者密码错误
            throw new DemoBaseException("用户名或者密码错误", 402);
        } else if (event instanceof AuthenticationFailureCredentialsExpiredEvent) {
            //验证通过，但是密码过期
            throw new DemoBaseException("密码过期", 402);
        } else if (event instanceof AuthenticationFailureDisabledEvent) {
            //验证过了但是账户被禁用
            throw new DemoBaseException("账户被禁用", 402);
        } else if (event instanceof AuthenticationFailureExpiredEvent) {
            //验证通过了，但是账号已经过期
            throw new DemoBaseException("账号已经过期", 402);
        } else if (event instanceof AuthenticationFailureLockedEvent) {
            //账户被锁定
            throw new DemoBaseException("账户被锁定", 402);
        } else if (event instanceof AuthenticationFailureProviderNotFoundEvent) {
            //配置错误，没有合适的AuthenticationProvider来处理登录验证
            throw new DemoBaseException("配置错误", 402);
        } else if (event instanceof AuthenticationFailureProxyUntrustedEvent) {
            //代理不受信任，用于Oauth、CAS这类三方验证的情形，多属于配置错误
            throw new DemoBaseException("代理不受信任", 402);
        } else if (event instanceof AuthenticationFailureServiceExceptionEvent) {
            //其他任何在AuthenticationManager中内部发生的异常都会被封装成此类
            throw new DemoBaseException("内部发生错误", 402);
        }
    }
}