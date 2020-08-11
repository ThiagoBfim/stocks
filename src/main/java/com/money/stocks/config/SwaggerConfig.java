package com.money.stocks.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI(@Value("${build.version}") String appVersion) {
        return new OpenAPI()
                .info(new Info()
                        .title("Stocks API")
                        .version(appVersion)
                        .license(new License().name("MIT License").url("https://raw.githubusercontent.com/ThiagoBfim/stocks/master/LICENSE")));
    }
}
