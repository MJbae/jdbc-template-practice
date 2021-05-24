package mj.jdbc_template_test.config;

import mj.jdbc_template_test.web.OAuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new OAuthInterceptor())
                .addPathPatterns("/users/**").excludePathPatterns("/login/**");
    }
}
