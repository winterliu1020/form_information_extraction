package cn.edu.nchu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by liuwentao on 2020-04-16 12:15
 */
@Controller
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/modelImages/**").addResourceLocations("file:/Users/liuwentao/Code/upload/model/");
        registry.addResourceHandler("/instanceImages/**").addResourceLocations("file:/Users/liuwentao/Code/upload/instance/");
    }
}

