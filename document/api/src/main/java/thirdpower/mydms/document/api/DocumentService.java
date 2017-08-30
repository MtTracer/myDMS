package thirdpower.mydms.document.api;

import java.util.List;
import java.util.Optional;

import com.google.common.io.ByteSource;

public interface DocumentService {

  Optional<Document> find(long id);

  List<Document> findAll(DocumentFilter filter);

  Optional<ByteSource> readContents(long id);

  Document create(Document document, ByteSource contents) throws DocumentServiceException;

  Document update(Document document);

  void updateContents(long id, ByteSource contents) throws DocumentServiceException;

  boolean delete(long id) throws DocumentServiceException;
}
