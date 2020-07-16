package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;

@ComponentScan(basePackages = {"com.controller","com.service"})
@EnableWebMvc //스프링 mvc 활성화
public class MvcConfig implements WebMvcConfigurer{

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	//viewResolvers 관리? 설정 하는곳.
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.viewResolver(tilesViewResolver());
		registry.viewResolver(viewResolver());
		
		//registry.jsp("/WEB-INF/views/", ".jsp");
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/writeForm").setViewName("TILES/writeForm");
	}
	
	
	//tilesViewResolver 를 setOrder(1) 순위로 잡아놨으니, 
	//모든 view 들은 tiles.xml 에서 정의한 definition에서 binding 될 jsp 등의 view 파일을 먼저 찾게된다. 
	@Bean
	public UrlBasedViewResolver tilesViewResolver() {
		UrlBasedViewResolver tilesViewResolver = new UrlBasedViewResolver();
		tilesViewResolver.setViewClass(org.springframework.web.servlet.view.tiles3.TilesView.class);
		tilesViewResolver.setOrder(1);
		return tilesViewResolver;
	}
	//만약 tiles.xml 에서 view 파일을 못찾게되면 2순위로 지정한 InternalResourceViewResolver 에서 해당 view 파일을 찾게된다.
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		resolver.setOrder(2);
		return resolver;
	}
	
	@Bean
	public TilesConfigurer tilesConfigurer() {
		TilesConfigurer tilesConfigurer = new TilesConfigurer();
		//tilesConfigurer.setDefinitions("classpath:com/config/tills.xml");
		tilesConfigurer.setDefinitions("classpath:com/config/tiles.xml");
		
		return tilesConfigurer;
	}

	
}
