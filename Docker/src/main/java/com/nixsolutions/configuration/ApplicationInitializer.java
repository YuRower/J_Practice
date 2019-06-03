package com.nixsolutions.configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.nixsolutions.security.SecurityConfiguration;

public class ApplicationInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext container) {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		ContextLoaderListener contextLoaderListener = new ContextLoaderListener(context);
		container.addListener(contextLoaderListener);
		context.register(AppConfig.class);
		context.register(SecurityConfiguration.class);
		context.setServletContext(container);
		ServletRegistration.Dynamic servlet = container.addServlet("dispatcher", new DispatcherServlet(context));
		servlet.setLoadOnStartup(1);
		servlet.addMapping("/");

		/*
		 * ServletRegistration.Dynamic wwebserviceDispatcher =
		 * container.addServlet("jersey-serlvet", new SpringServlet());
		 * wwebserviceDispatcher.addMapping("/rest/*");
		 */

	}

}