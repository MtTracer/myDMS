package thirdpower.mydms.inject.api;

import thirdpower.mydms.config.GlobalConfig;

public interface BindingConfigProvider<T> {

  void provideBindingConfig(BindingConfigRegistry<T> registry, GlobalConfig config);
}
