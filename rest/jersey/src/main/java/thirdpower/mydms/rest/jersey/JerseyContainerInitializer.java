package thirdpower.mydms.rest.jersey;

import java.util.Set;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.servlet.ServletContainer;

import com.google.auto.service.AutoService;

@AutoService(ServletContainerInitializer.class)
public class JerseyContainerInitializer implements ServletContainerInitializer {

  @Override
  public void onStartup(final Set<Class<?>> c, final ServletContext ctx) throws ServletException {
    final Dynamic servletConfig = ctx.addServlet("JerseyServlet", ServletContainer.class);
    servletConfig.setInitParameter(Application.class.getName(), MyDmsJerseyConfig.class.getName());
    servletConfig.addMapping("/rest/*");

  }

}
