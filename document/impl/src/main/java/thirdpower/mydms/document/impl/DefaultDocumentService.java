package thirdpower.mydms.document.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import javax.inject.Inject;

import thirdpower.mydms.document.api.Document;
import thirdpower.mydms.document.api.DocumentFilter;
import thirdpower.mydms.document.api.DocumentService;
import thirdpower.mydms.filestore.api.FileStoreService;

class DefaultDocumentService implements DocumentService {

  private final FileStoreService filestoreService;

  @Inject
  DefaultDocumentService(final FileStoreService filestoreService) {
    this.filestoreService = checkNotNull(filestoreService);
  }

  @Override
  public Document find(final long id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Document> findAll(final DocumentFilter filter) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Document save(final Document document) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean delete(final long id) {
    // TODO Auto-generated method stub
    return false;
  }

}
