package com.app.demo.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Interceptor処理クラス
 *
 * @author y_ha
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private JwtAuthorizationFilter authenticationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // すべての要求をブロックし、@LoginaRequiredコメントがあるかどうかを判断することにより登録が必要かどうかを決定
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/**");
    }
}