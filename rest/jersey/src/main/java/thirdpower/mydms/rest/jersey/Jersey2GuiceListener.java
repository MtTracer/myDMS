package thirdpower.mydms.rest.jersey;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.google.inject.Injector;
import com.squarespace.jersey2.guice.JerseyGuiceUtils;

import thirdpower.mydms.inject.api.DependencyFactory;

@WebListener
public class Jersey2GuiceListener implements ServletContextListener {

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    Injector injector = DependencyFactory.getInstance(Injector.class);
    JerseyGuiceUtils.install(injector);
  }
}
