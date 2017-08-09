package thirdpower.mydms.filestore.filesystem.pathstrategies;

import com.google.auto.service.AutoService;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Provides;

@AutoService(Module.class)
public class PathStrategiesModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(SubfolderDistributionStrategy.class);
  }

  // TODO read config from file
  @Provides
  private SubfolderDistributionConfiguration provideSubfolderDistributionConfig() {
    return new SubfolderDistributionConfiguration();
  }

}
