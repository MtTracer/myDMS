package thirdpower.mydms.persistence.h2embedded;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.google.inject.persist.PersistService;

import thirdpower.mydms.inject.api.DependencyFactory;

@WebListener
public class GuicePersistListener implements ServletContextListener {

  @Override
  public void contextInitialized(ServletContextEvent sce) {
     PersistService persistService = DependencyFactory.getInstance(PersistService.class);
     persistService.start();
  }
  
  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    PersistService persistService = DependencyFactory.getInstance(PersistService.class);
    persistService.stop();
  }
}
