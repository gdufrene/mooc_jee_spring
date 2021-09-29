package fr.eservices.week402.app;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebAppInitializer implements WebApplicationInitializer {
	
	@Override
	public void onStartup(ServletContext container) throws ServletException {
		/* *
		AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
		dispatcherContext.register( AppConfig.class );
		container.addListener( new ContextLoaderListener(dispatcherContext) );
		ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher", DispatcherServlet.class);
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/app/*");
		/* */
	}

}
