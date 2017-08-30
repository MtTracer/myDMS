package thirdpower.mydms.inject.guice;

import javax.servlet.annotation.WebListener;

import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

import thirdpower.mydms.inject.api.DependencyFactory;

@WebListener
public class MyDmsGuiceListener extends GuiceServletContextListener {

  @Override
  protected Injector getInjector() {
    return DependencyFactory.getInstance(Injector.class);
  }

}
