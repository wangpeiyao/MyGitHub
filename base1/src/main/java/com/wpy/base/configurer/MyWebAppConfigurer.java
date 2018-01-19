package com.wpy.base.configurer;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration 
//@EnableWebMvc//无需使用该注解，否则会覆盖掉SpringBoot的默认配置值
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter {
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("/myresources/**").addResourceLocations("classpath:/myresources/");
	    super.addResourceHandlers(registry);
	}
}
