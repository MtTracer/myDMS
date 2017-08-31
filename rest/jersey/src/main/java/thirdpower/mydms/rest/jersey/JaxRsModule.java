package thirdpower.mydms.rest.jersey;

import com.google.auto.service.AutoService;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.servlet.RequestScoped;

import thirdpower.mydms.rest.jersey.querymeta.PagedQueryProvider;
import thirdpower.mydms.util.PagedQuery;

@AutoService(Module.class)
public class JaxRsModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(PagedQuery.class).toProvider(PagedQueryProvider.class).in(RequestScoped.class);
  }

}
