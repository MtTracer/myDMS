package thirdpower.mydms.document.api;

import java.util.List;

public interface DocumentService {

  Document find(long id);

  List<Document> findAll(DocumentFilter filter);

  Document save(Document document);

  boolean delete(long id);
}
