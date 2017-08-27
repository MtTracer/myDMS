package thirdpower.mydms.document.persistence;

import com.google.auto.service.AutoService;

import thirdpower.mydms.persistence.api.PersistenceClassProvider;
import thirdpower.mydms.persistence.api.PersistenceClassRegistration;

@AutoService(PersistenceClassProvider.class)
public class DocumentPersistenceClassesProvider implements PersistenceClassProvider {

  @Override
  public void registerPersistenceClasses(final PersistenceClassRegistration registration) {
    registration.register(DocumentEntity.class);
  }

}
