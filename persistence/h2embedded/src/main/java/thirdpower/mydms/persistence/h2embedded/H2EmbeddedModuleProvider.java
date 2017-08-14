package thirdpower.mydms.persistence.h2embedded;

import java.nio.file.Path;

import com.google.auto.service.AutoService;
import com.google.inject.Module;

import thirdpower.mydms.config.GlobalConfig;
import thirdpower.mydms.inject.api.BindingConfigProvider;
import thirdpower.mydms.inject.api.BindingConfigRegistry;

@AutoService(BindingConfigProvider.class)
public class H2EmbeddedModuleProvider implements BindingConfigProvider<Module> {

  @Override
  public void provideBindingConfig(final BindingConfigRegistry<Module> registry,
      final GlobalConfig config) {
    final Path myDmsDb = config.getMyDmsDb();
    registry.register(new H2EmbeddedModule(myDmsDb));

  }

}
