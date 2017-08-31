package thirdpower.mydms.rest.jersey.querymeta;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

import thirdpower.mydms.rest.api.MetaWrapper;
import thirdpower.mydms.rest.api.Meta;

public class MetaWrapperFilter implements ContainerResponseFilter {

  private Provider<Meta> metaProvider;

  @Inject
  MetaWrapperFilter(Provider<Meta> metaProvider) {
    this.metaProvider = checkNotNull(metaProvider);
  }

  @Override
  public void filter(ContainerRequestContext requestContext,
      ContainerResponseContext responseContext) throws IOException {
    Meta meta = metaProvider.get();
    if(null==meta) {
      return;
    }
    
    Object entity = responseContext.getEntity();
    MetaWrapper wrapper = new MetaWrapper(entity, meta);
    responseContext.setEntity(wrapper);

  }

}
