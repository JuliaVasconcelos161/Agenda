package com.github.juliavasconcelos161.scheduleapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(ApiInfo.DEFAULT)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.github.juliavasconcelos161.scheduleapi.api.rest"))
                .build();
    }
}
