package thirdpower.mydms.rest.jersey;

import com.google.auto.service.AutoService;
import com.google.inject.Module;
import com.google.inject.servlet.ServletModule;
import com.squarespace.jersey2.guice.JerseyGuiceModule;

import thirdpower.mydms.config.GlobalConfig;
import thirdpower.mydms.inject.api.BindingConfigProvider;
import thirdpower.mydms.inject.api.BindingConfigRegistry;

@AutoService(BindingConfigProvider.class)
public class Jersey2GuiceModuleProvider implements BindingConfigProvider<Module> {

  private static final String DEFAULT_JERSEY_HK2_LOCATOR = "__HK2_Generated_0";

  @Override
  public void provideBindingConfig(final BindingConfigRegistry<Module> registry,
      final GlobalConfig config) {
    registry.register(new ServletModule())
      .register(new JerseyGuiceModule(DEFAULT_JERSEY_HK2_LOCATOR));
  }

}
