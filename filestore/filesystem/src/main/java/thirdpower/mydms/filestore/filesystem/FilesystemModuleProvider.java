package thirdpower.mydms.filestore.filesystem;

import com.google.auto.service.AutoService;
import com.google.inject.Module;

import thirdpower.mydms.config.GlobalConfig;
import thirdpower.mydms.inject.api.BindingConfigProvider;
import thirdpower.mydms.inject.api.BindingConfigRegistry;

@AutoService(BindingConfigProvider.class)
public class FilesystemModuleProvider implements BindingConfigProvider<Module> {

  @Override
  public void provideBindingConfig(final BindingConfigRegistry<Module> registry,
      final GlobalConfig config) {
    registry.register(new FilesystemModule(config.getMyDmsHome()));

  }

}
