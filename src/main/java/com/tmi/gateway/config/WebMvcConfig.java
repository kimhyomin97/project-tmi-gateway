package com.tmi.gateway.config;

import com.tmi.gateway.interceptor.AuthenticationInterceptor;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = { "classpath:/static/", "classpath:/public/", "classpath:/",
            "classpath:/resources/", "classpath:/META-INF/resources/", "classpath:/META-INF/resources/webjars/" };

    private final AuthenticationInterceptor authenticationInterceptor;

    private final List<String> addEndPointList = List.of("/**");

    private final List<String> excludeEndPointList = List.of("/login", "/error", "/favicon.ico", "/index.html", "/static/**");

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // /로 요청이 오면 /index.html로 forward
        registry.addViewController("/").setViewName("forward:/index.html");

        // 우선순위 높게 설정
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns(addEndPointList)
                .excludePathPatterns(excludeEndPointList);
    }
}
