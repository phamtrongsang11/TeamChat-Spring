package com.sangpt.teamchatspring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.sangpt.teamchatspring.middleware.JwtAuthenticationInterceptor;

@Configuration
public class JwtAuthenticationConfig implements WebMvcConfigurer {
    public final JwtAuthenticationInterceptor JwtAuthenticationInterception;

    @Autowired
    public JwtAuthenticationConfig(JwtAuthenticationInterceptor JwtAuthenticationInterception) {
        this.JwtAuthenticationInterception = JwtAuthenticationInterception;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(JwtAuthenticationInterception).addPathPatterns("/**");
    }

}
