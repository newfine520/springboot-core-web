package com.isg.springboottest.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
//@EnableSwagger
public class SwaggerConfig {
    //@Bean
//    public Docket createRestApi()
//    {
//       return  new Docket(DocumentationType.SWAGGER_2).pathMapping("/").select().apis().paths().build().apiDescriptionOrdering(new ApiInfoBuilder());
//    }
}
