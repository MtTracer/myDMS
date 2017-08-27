package thirdpower.mydms.document.impl;

import com.google.auto.service.AutoService;
import com.google.inject.AbstractModule;
import com.google.inject.Module;

import thirdpower.mydms.document.api.DocumentService;

@AutoService(Module.class)
public class DocumentModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(DocumentService.class).to(DefaultDocumentService.class);
  }

}
