package thirdpower.mydms.inject.guice;

import java.util.EnumSet;
import java.util.Set;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import com.google.auto.service.AutoService;
import com.google.inject.servlet.GuiceFilter;

@AutoService(ServletContainerInitializer.class)
public class GuiceServletContainerInitializer implements ServletContainerInitializer {

  @Override
  public void onStartup(final Set<Class<?>> c, final ServletContext ctx) throws ServletException {
    ctx.addFilter("GuiceFilter", GuiceFilter.class)
      .addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    ctx.addListener(MyDmsGuiceListener.class);

  }

}
