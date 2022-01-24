package com.lloydsbank.api.atmservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * The type Atm service config.
 */
@Configuration
public class AtmServiceConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry reg) {
        reg.addInterceptor(new MdcInterceptor());
    }

}
