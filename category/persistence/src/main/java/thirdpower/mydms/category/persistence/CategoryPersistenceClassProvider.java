package thirdpower.mydms.category.persistence;

import com.google.auto.service.AutoService;

import thirdpower.mydms.persistence.api.PersistenceClassProvider;
import thirdpower.mydms.persistence.api.PersistenceClassRegistration;

@AutoService(PersistenceClassProvider.class)
public class CategoryPersistenceClassProvider implements PersistenceClassProvider {

  @Override
  public void registerPersistenceClasses(final PersistenceClassRegistration registration) {
    registration.register(CategoryEntity.class);
  }

}
