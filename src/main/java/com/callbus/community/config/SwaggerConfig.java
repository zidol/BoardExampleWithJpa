package com.callbus.community.config;

import com.callbus.community.dto.board.BoardDto;
import com.callbus.community.dto.board.BoardUpdateForm;
import com.callbus.community.dto.heart.HeartDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private ApiKey apiKey() {
        return new ApiKey("JWT", "AUTHORIZATION", "header");
    }
    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }

    private Set<String> getConsumeContentTypes() {
        Set<String> consumes = new HashSet<>();
        consumes.add("application/json;charset=UTF-8");
        consumes.add("application/x-www-form-urlencoded");
        return consumes;
    }

    private Set<String> getProduceContentTypes() {
        Set<String> produces = new HashSet<>();
        produces.add("application/json;charset=UTF-8");
        return produces;
    }
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .alternateTypeRules(
                        AlternateTypeRules.newRule(Pageable.class, Page.class),
                        AlternateTypeRules.newRule(BoardDto.class, BoardDtoSwagger.class),
                        AlternateTypeRules.newRule(BoardUpdateForm.class, BoardDtoSwagger.class),
                        AlternateTypeRules.newRule(HeartDto.class, HeartDtoSwagger.class)
                )
                .apiInfo(apiInfo())
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .produces(getProduceContentTypes())
                .consumes(getConsumeContentTypes())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Callbus 게시판 Rest Api",
                "Some custom description of API.",
                "1.0",
                "Terms of service",
                new Contact("Callbus Community Rest Api", "https://callbus.com/", "ajh3166@gmail.com"),
                "License of API",
                "API license URL",
                Collections.emptyList());
    }

    @Getter @Setter
    @ApiModel
    static class Page {
        @ApiModelProperty(value = "페이지 번호(0..N)")
        private Integer page;

        @ApiModelProperty(value = "페이지 크기", allowableValues = "range[0, 100]")
        private Integer size;
    }

    @Getter @Setter
    @ApiModel
    static class BoardDtoSwagger {

        @ApiModelProperty
        @NotEmpty
        @Size(min = 3, max = 50)
        private String subject;

        @ApiModelProperty(value = "페이지 크기", allowableValues = "range[0, 100]")
        @NotEmpty
        @Size(min = 3, max = 500)
        private String contents;
    }

    @Getter @Setter
    @ApiModel
    static class HeartDtoSwagger {

        @NotNull
        private Long boardId;
    }


}

