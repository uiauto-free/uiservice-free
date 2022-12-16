package com.uiautofree.common.config.global;

import com.uiautofree.common.config.global.requestHandler.RequestAuthHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AuthWebConfiguration implements WebMvcConfigurer {
    @Autowired
    RequestAuthHandler requestAuthHandler;

    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry) {
        interceptorRegistry.addInterceptor(requestAuthHandler).excludePathPatterns("/api/auth/login");
    }
}
