package com.adverpix.ecommerce.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//WE added this class to serve images from the 'uploaded-images' directory
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serve images from the 'uploaded-images' directory
        registry.addResourceHandler("/static/uploaded-images/**")//registry.addResourceHandler is used to register resource handlers
                .addResourceLocations("file:uploaded-images/");//addResourceLocations is used to specify the location of the resources
    }
}