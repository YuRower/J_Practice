package com.nixsolutions.configuration;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.ws.config.annotation.EnableWs;

import com.nixsolutions.converter.RoleToUserProfileConverter;
import com.nixsolutions.webservices.soap.UserSoapService;
import com.nixsolutions.webservices.soap.UserSoapServiceImpl;

@Configuration
@EnableWebMvc
@ComponentScan({ "com.nixsolutions" })
@EnableWs
@ImportResource({ "classpath:META-INF/cxf/cxf.xml", "classpath:META-INF/cxf/cxf-servlet.xml" })
public class AppConfig extends WebMvcConfigurerAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger(AppConfig.class);

	@Bean(name = Bus.DEFAULT_BUS_ID)
	public SpringBus springBus() {
		return new SpringBus();
	}

	@Bean
	public Endpoint endpoint() {
		Endpoint endpoint = new EndpointImpl(springBus(), userOperations());
		endpoint.publish("/users");
		return endpoint;
	}

	@Bean
	public UserSoapService userOperations() {
		return new UserSoapServiceImpl();
	}

	@Autowired
	RoleToUserProfileConverter roleToUserProfileConverter;

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		LOGGER.debug("Configure ViewResolvers to deliver preferred views.");
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		registry.viewResolver(viewResolver);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		LOGGER.debug("Configure ResourceHandlers to serve static resources like CSS/ Javascript.");

		registry.addResourceHandler("/static/**").addResourceLocations("/static/");
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(roleToUserProfileConverter);
	}

	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages");
		return messageSource;
	}

	@Override
	public void configurePathMatch(PathMatchConfigurer matcher) {
		matcher.setUseRegisteredSuffixPatternMatch(true);
	}
}
