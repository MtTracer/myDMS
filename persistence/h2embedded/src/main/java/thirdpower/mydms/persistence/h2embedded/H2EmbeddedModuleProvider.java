package thirdpower.mydms.persistence.h2embedded;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ServiceLoader;

import com.google.auto.service.AutoService;
import com.google.inject.Module;

import thirdpower.mydms.config.GlobalConfig;
import thirdpower.mydms.inject.api.BindingConfigProvider;
import thirdpower.mydms.inject.api.BindingConfigRegistry;
import thirdpower.mydms.persistence.api.PersistenceClassProvider;

@AutoService(BindingConfigProvider.class)
public class H2EmbeddedModuleProvider implements BindingConfigProvider<Module> {

  @Override
  public void provideBindingConfig(final BindingConfigRegistry<Module> registry,
      final GlobalConfig config) {
    setupDynamicPersistenceXml(config.getMyDmsHome());

    final Path myDmsDb = config.getMyDmsDb();
    registry.register(new H2EmbeddedModule(myDmsDb));

  }

  private void setupDynamicPersistenceXml(final Path workDir) throws Error {
    final ServiceLoader<PersistenceClassProvider> providerLoader =
        ServiceLoader.load(PersistenceClassProvider.class);
    final DynamicPersistenceXml dynamicPersistenceXml = new DynamicPersistenceXml(providerLoader);
    try {
      dynamicPersistenceXml.setupPersistenceXml(Thread.currentThread(), workDir);
    } catch (final IOException e) {
      // TODO handle error
      throw new Error("Could not setup persistence.");
    }
  }


}
