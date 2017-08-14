package thirdpower.mydms.inject.guice;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import com.google.auto.service.AutoService;
import com.google.common.collect.ImmutableList;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

import thirdpower.mydms.config.GlobalConfig;
import thirdpower.mydms.config.GlobalConfigFactory;
import thirdpower.mydms.inject.api.BindingConfigProvider;
import thirdpower.mydms.inject.api.BindingConfigRegistry;
import thirdpower.mydms.inject.api.DependencyFactory.DependencyResolver;

@AutoService(DependencyResolver.class)
public class GuiceDependencyResolver implements DependencyResolver {

  private final Injector injector;

  public GuiceDependencyResolver() {
    final List<Module> modules = loadModules();
    injector = Guice.createInjector(modules);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  private List<Module> loadModules() {
    final ModuleRegistry moduleRegistry = new ModuleRegistry();

    final GlobalConfig globalConfig = GlobalConfigFactory.load();
    final ServiceLoader<BindingConfigProvider> bindingConfigProviders =
        ServiceLoader.load(BindingConfigProvider.class);
    for (final BindingConfigProvider bindingConfigProvider : bindingConfigProviders) {
      bindingConfigProvider.provideBindingConfig(moduleRegistry, globalConfig);
    }

    final ServiceLoader<Module> moduleLoader = ServiceLoader.load(Module.class);
    return ImmutableList.<Module>builder()
      .addAll(moduleRegistry.getModules())
      .addAll(moduleLoader)
      .build();
  }

  @Override
  public <T> T getInstance(final Class<T> clazz) {
    return injector.getInstance(clazz);
  }

  private static final class ModuleRegistry implements BindingConfigRegistry<Module> {

    private final List<Module> modules = new ArrayList<>();

    @Override
    public BindingConfigRegistry<Module> register(final Module bindingConfig) {
      modules.add(bindingConfig);
      return this;
    }

    public List<Module> getModules() {
      return modules;
    }
  }

}
