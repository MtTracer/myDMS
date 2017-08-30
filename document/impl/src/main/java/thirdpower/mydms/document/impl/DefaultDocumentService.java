package thirdpower.mydms.document.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;

import com.google.common.io.ByteSource;
import com.google.inject.persist.Transactional;
import com.google.j2objc.annotations.LoopTranslation;

import thirdpower.mydms.document.api.Document;
import thirdpower.mydms.document.api.DocumentFilter;
import thirdpower.mydms.document.api.DocumentService;
import thirdpower.mydms.document.api.DocumentServiceException;
import thirdpower.mydms.document.persistence.DocumentDAO;
import thirdpower.mydms.document.persistence.DocumentEntity;
import thirdpower.mydms.filestore.api.FileReference;
import thirdpower.mydms.filestore.api.FileStoreException;
import thirdpower.mydms.filestore.api.FileStoreService;

class DefaultDocumentService implements DocumentService {

  private final FileStoreService filestoreService;
  private final DocumentDAO documentDAO;

  @Inject
  DefaultDocumentService(final FileStoreService filestoreService, final DocumentDAO documentDAO) {
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
    return new Document(entity.getId(), entity.getName());
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
      filestoreService.delete(id, entity.getName());

    } catch (final FileStoreException e) {
      throw new DocumentServiceException("Could not delete document file.", e);
    }

    documentDAO.remove(entity);
    return true;
  }

  @Override
  public Optional<ByteSource> readContents(final long id) {
    final DocumentEntity entity = documentDAO.findById(id);
    if (null == entity) {
      return Optional.empty();
    }

    final Optional<FileReference> fileRef = filestoreService.read(id, entity.getName());
    return fileRef.map(FileReference::getContents);
  }

  @Override
  @Transactional
  public Document create(final Document document, final ByteSource contents)
      throws DocumentServiceException {
    DocumentEntity entity = new DocumentEntity();
    entity.setName(document.getName());
    entity = documentDAO.persist(entity);

    try {
      filestoreService.save(entity.getId(), entity.getName(), contents);
    } catch (final FileStoreException e) {
      throw new DocumentServiceException("Could not save document.", e);
    }

    return fromEntity(entity);
  }

  @Override
  public Document update(final Document document) {
    DocumentEntity entity = documentDAO.findById(document.getId());
    checkArgument(null != entity, "Can't find document with id %s for update.", document.getId());
    entity.setName(document.getName());
    entity = documentDAO.persist(entity);
    return fromEntity(entity);
  }

  @Override
  public void updateContents(final long id, final ByteSource contents)
      throws DocumentServiceException {
    final DocumentEntity entity = documentDAO.findById(id);
    checkArgument(null != entity, "Can't find document with id %s for update.", id);

    try {
      filestoreService.save(id, entity.getName(), contents);
    } catch (final FileStoreException e) {
      throw new DocumentServiceException("Could not update document contents.", e);
    }

  }

}
