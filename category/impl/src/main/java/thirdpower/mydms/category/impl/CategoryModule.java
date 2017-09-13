package thirdpower.mydms.category.impl;

import com.google.auto.service.AutoService;
import com.google.inject.AbstractModule;
import com.google.inject.Module;

import thirdpower.mydms.category.api.CategoryService;

@AutoService(Module.class)
public class CategoryModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(CategoryService.class).to(DefaultCategoryService.class);
  }
}
