package thirdpower.mydms.inject.api;

public interface BindingConfigRegistry<T> {
  BindingConfigRegistry<T> register(T bindingConfig);
}
