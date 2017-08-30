package thirdpower.mydms.rest.jersey;

import org.glassfish.jersey.server.ResourceConfig;


public class MyDmsJerseyConfig extends ResourceConfig {

  public MyDmsJerseyConfig() {
    packages(true, "thirdpower.mydms");
  }

}
