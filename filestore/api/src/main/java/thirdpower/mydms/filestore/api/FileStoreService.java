package thirdpower.mydms.filestore.api;

public interface FileStoreService {

  public FileReference save(FileReference fileReference) throws FileStoreException;

  public void delete(long fileReferenceId) throws FileStoreException;
}
