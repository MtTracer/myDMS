package thirdpower.mydms.category.persistence;

import com.google.auto.service.AutoService;
import com.google.inject.AbstractModule;
import com.google.inject.Module;

@AutoService(Module.class)
public class CategoryPersistenceModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(CategoryRepository.class);
  }

}
