package thirdpower.mydms.filestore.api;

public class FileStoreException extends Exception {

  private static final long serialVersionUID = -5073620366528164698L;

  public FileStoreException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public FileStoreException(final String message) {
    super(message);
  }

}
