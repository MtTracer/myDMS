package thirdpower.mydms.document.api;

public class DocumentServiceException extends Exception {

  private static final long serialVersionUID = 445661552861036987L;

  public DocumentServiceException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public DocumentServiceException(final String message) {
    super(message);
  }

}
