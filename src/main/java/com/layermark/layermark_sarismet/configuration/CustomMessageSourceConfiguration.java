package com.layermark.layermark_sarismet.configuration;


import com.layermark.layermark_sarismet.generic.CustomMessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomMessageSourceConfiguration {

    @Bean
    public CustomMessageSource customMessageSource(){
        CustomMessageSource a = new CustomMessageSource();
        a.setBasename("classpath:messages");
        a.setDefaultEncoding("UTF-8");
        a.setUseCodeAsDefaultMessage(true);
        return  a;
    }
}