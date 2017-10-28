package thirdpower.mydms.document.impl;

import com.google.common.io.ByteSource;
import com.google.inject.persist.Transactional;
import thirdpower.mydms.document.api.Document;
import thirdpower.mydms.document.api.DocumentFilter;
import thirdpower.mydms.document.api.DocumentService;
import thirdpower.mydms.document.api.DocumentServiceException;
import thirdpower.mydms.document.persistence.DocumentEntity;
import thirdpower.mydms.document.persistence.DocumentRepository;
import thirdpower.mydms.filestore.api.FileReference;
import thirdpower.mydms.filestore.api.FileStoreException;
import thirdpower.mydms.filestore.api.FileStoreService;

import javax.inject.Inject;
import java.util.Optional;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

class DefaultDocumentService implements DocumentService {

  private final FileStoreService filestoreService;
  private final DocumentRepository documentDAO;

  @Inject
  DefaultDocumentService(final FileStoreService filestoreService, final DocumentRepository documentDAO) {
    this.documentDAO = checkNotNull(documentDAO);
    this.filestoreService = checkNotNull(filestoreService);
  }

  @Override
  public Optional<Document> find(final long id) {
    final DocumentEntity entity = documentDAO.findById(id);
    if(null==entity) {
      return Optional.empty();
    }
    final Document document = fromEntity(entity);
    return Optional.of(document);
  }

  private Document fromEntity(final DocumentEntity entity) {
    return new Document(entity.getId(), entity.getFileName());
  }

  @Override
  public Stream<Document> findAll(final DocumentFilter filter) {
    return documentDAO.findAll().stream().map(this::fromEntity);
  }

  @Override
  @Transactional
  public boolean delete(final long id) throws DocumentServiceException {
    final DocumentEntity entity = documentDAO.findById(id);
    if (null == entity) {
      return false;
    }

    try {
      filestoreService.delete(id, entity.getFileName());

    } catch (final FileStoreException e) {
      throw new DocumentServiceException("Could not delete document file.", e);
    }

      documentDAO.delete(entity);
    return true;
  }

  @Override
  public Optional<ByteSource> readContents(final long id) {
    final DocumentEntity entity = documentDAO.findById(id);
    if (null == entity) {
      return Optional.empty();
    }

    final Optional<FileReference> fileRef = filestoreService.read(id, entity.getFileName());
    return fileRef.map(FileReference::getContents);
  }

  @Override
  @Transactional
  public Document create(final Document document, final ByteSource contents)
      throws DocumentServiceException {
    DocumentEntity entity = new DocumentEntity();
    entity.setFileName(document.getName());
      entity = documentDAO.create(entity);

    try {
      filestoreService.save(entity.getId(), entity.getFileName(), contents);
    } catch (final FileStoreException e) {
      throw new DocumentServiceException("Could not save document.", e);
    }

    return fromEntity(entity);
  }

  @Override
  public Document update(final Document document) {
    DocumentEntity entity = documentDAO.findById(document.getId());
    checkArgument(null != entity, "Can't find document with id %s for update.", document.getId());
    entity.setFileName(document.getName());
      entity = documentDAO.update(entity);
    return fromEntity(entity);
  }

  @Override
  public void updateContents(final long id, final ByteSource contents)
      throws DocumentServiceException {
    final DocumentEntity entity = documentDAO.findById(id);
    checkArgument(null != entity, "Can't find document with id %s for update.", id);

    try {
      filestoreService.save(id, entity.getFileName(), contents);
    } catch (final FileStoreException e) {
      throw new DocumentServiceException("Could not update document contents.", e);
    }

  }

}
