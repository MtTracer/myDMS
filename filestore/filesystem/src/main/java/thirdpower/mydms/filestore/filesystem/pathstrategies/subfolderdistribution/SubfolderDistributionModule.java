package thirdpower.mydms.filestore.filesystem.pathstrategies.subfolderdistribution;

import com.google.auto.service.AutoService;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.multibindings.MapBinder;

import thirdpower.mydms.filestore.filesystem.pathstrategies.PathStrategy;

@AutoService(Module.class)
public class SubfolderDistributionModule extends AbstractModule {

  public static final String STRATEGY_NAME = "SubfolderDistribution";

  @Override
  protected void configure() {
    MapBinder.newMapBinder(binder(), String.class, PathStrategy.class)
      .addBinding(STRATEGY_NAME)
      .to(SubfolderDistributionStrategy.class);
    bind(SubfolderDistributionConfiguration.class)
      .toProvider(ParsedSubfolderDistributionConfigProvider.class);
  }

}
