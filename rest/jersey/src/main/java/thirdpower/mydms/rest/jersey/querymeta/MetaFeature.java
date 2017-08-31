package thirdpower.mydms.rest.jersey.querymeta;

import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;

public class MetaFeature implements DynamicFeature {

  @Override
  public void configure(ResourceInfo resourceInfo, FeatureContext context) {
    Class<?> returnType = resourceInfo.getResourceMethod().getReturnType();
    if(Iterable.class.isAssignableFrom(returnType) || returnType.isArray()) {
      context.register(MetaWrapperFilter.class);
    }
  }

}
