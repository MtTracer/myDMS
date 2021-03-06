package thirdpower.mydms.document.persistence;

import com.google.auto.service.AutoService;
import com.google.inject.AbstractModule;
import com.google.inject.Module;

@AutoService(Module.class)
public class DocumentPersistenceModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(DocumentRepository.class);
  }

}
