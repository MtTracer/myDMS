package thirdpower.mydms.filestore.api;

import com.google.common.io.ByteSource;

import java.util.Optional;

public interface FileStoreService {

  /**
   * Creates a {@link FileReference} for the given id and filename containing a {@link ByteSource}
   * for reading file contents.
   *
   * @param fileReferenceId id of the file
   * @param filename name of the file
   * @return if existing a {@link FileReference} pointing to the requested file wrapped in an
   *         {@link Optional}, if not found an {@link Optional#empty()}
   */
  Optional<FileReference> read(long fileReferenceId, String filename);

  /**
   * Writes the given contents to the file referenced by the given id and filename. Existing files
   * will be overwritten, new files will be created.
   *
   * @param fileReferenceId id of the file
   * @param filename name of the file
   * @param contents file contents to write, must not point to an already existing file given by
   *        {@link #read(long, String)}
   * @return {@link FileReference} pointing to the written file
   * @throws FileStoreException if there was an error while writing the file or if the given
   *         contents point to the same file that should be written
   */
  FileReference save(long fileReferenceId, String filename, ByteSource contents)
      throws FileStoreException;

  /**
   * Removes the file referenced by the given id and filename.
   *
   * @param fileReferenceId id of the file
   * @param filename name of the file
   * @throws FileStoreException error while removing the file
   */
  void delete(long fileReferenceId, String filename) throws FileStoreException;
}
