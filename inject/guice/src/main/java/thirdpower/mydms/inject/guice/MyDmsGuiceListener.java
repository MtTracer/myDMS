package thirdpower.mydms.inject.guice;

import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

import thirdpower.mydms.inject.api.DependencyFactory;

public class MyDmsGuiceListener extends GuiceServletContextListener {

  @Override
  protected Injector getInjector() {
    return DependencyFactory.getInstance(Injector.class);
  }

}
