//package mj.jdbc_template_test.config;
//
//import mj.jdbc_template_test.oauth.OAuthInterceptor;
//import mj.jdbc_template_test.util.OauthUtil;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class InterceptorConfig implements WebMvcConfigurer {
//    private final OauthUtil oauthUtil;
//
//    public InterceptorConfig(OauthUtil oauthUtil) {
//        this.oauthUtil = oauthUtil;
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//
//        registry.addInterceptor(new OAuthInterceptor(oauthUtil))
//                .addPathPatterns("/api/**")
//                .excludePathPatterns("/api/login/**")
//                .excludePathPatterns("/api/test_users/**");
//
//    }
//}
