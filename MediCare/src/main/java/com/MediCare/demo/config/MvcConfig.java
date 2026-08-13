package com.MediCare.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    private final RolFormatter rolFormatter;

    public MvcConfig(RolFormatter rolFormatter) {
        this.rolFormatter = rolFormatter;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/acceso-denegado").setViewName("acceso-denegado");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(rolFormatter);
    }
}
