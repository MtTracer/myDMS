package thirdpower.mydms.rest.jersey;

import org.glassfish.jersey.server.ResourceConfig;

import thirdpower.mydms.rest.jersey.querymeta.MetaFeature;


public class MyDmsJerseyConfig extends ResourceConfig {

  public MyDmsJerseyConfig() {
    packages(true, "thirdpower.mydms");
    register(MetaFeature.class);
  }

}
