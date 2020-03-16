package cn.edu.nchu.config;

import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by liuwentao on 2020-03-15 21:55
 */
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(new AuthInterceptor());
        registration.addPathPatterns("/**");         //所有路径都被拦截
        registration.excludePathPatterns("/","/login","/toRegister","/register",
                "/assets/**","/css/**","/errorPage/**",
                "/images/**","/js/**","/plugins/**");       //添加不拦截路径

    }
}
