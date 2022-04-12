package indi.shenqqq.bbs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @Author Shen Qi
 * @Date 2022/4/5 16:20
 * @Description XX
 */

//http://localhost:8080/swagger-ui/index.html#
@Configuration
@EnableOpenApi
public class SwaggerConfig {
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo()).enable(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage("indi.shenqqq.bbs"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(Config.PROJECT_NAME)
                .description(Config.PROJECT_DESCRIPTION)
                .contact(new Contact(Config.AUTHOR_NAME, Config.AUTHOR_WEBSITE, Config.AUTHOR_EMAIL))
                .version(Config.PROJECT_VERSION)
                .build();
    }
}