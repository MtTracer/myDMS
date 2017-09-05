package thirdpower.mydms.filestore.filesystem;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import javax.inject.Inject;

import com.google.common.io.ByteSource;

import thirdpower.mydms.filestore.api.FileReference;
import thirdpower.mydms.filestore.api.FileStoreException;
import thirdpower.mydms.filestore.api.FileStoreService;
import thirdpower.mydms.filestore.filesystem.pathstrategies.PathStrategy;

public class FilesystemFileStoreService implements FileStoreService {

  private final FilesystemFileStoreConfiguration config;

  @Inject
  FilesystemFileStoreService(final FilesystemFileStoreConfiguration config) {
    this.config = checkNotNull(config);
  }

  @Override
  public Optional<FileReference> read(final long fileReferenceId, final String filename) {
    final Path targetPath = createTargetPath(fileReferenceId, filename);
    final File targetFile = targetPath.toFile();
    if (!targetFile.isFile()) {
      return Optional.empty();
    }

    final ByteSource source = com.google.common.io.Files.asByteSource(targetFile);
    final FileReference fileReference = FileReference.builder()
      .setId(fileReferenceId)
      .setFileName(filename)
      .setContentsSource(source)
      .build();
    return Optional.of(fileReference);
  }

  @Override
  public FileReference save(final long fileReferenceId, final String filename,
      final ByteSource contents) throws FileStoreException {
    final Path filePath = createTargetPath(fileReferenceId, filename);
    final File targetFile = filePath.toFile();

    try {
      Files.createDirectories(filePath.getParent());
    } catch (final IOException e) {
      throw new FileStoreException("Could not create directories of target file: " + targetFile, e);
    }

    try (OutputStream os = Files.newOutputStream(filePath)) {
      contents.copyTo(os);
    } catch (final IOException e) {
      throw new FileStoreException("Could not write new contents to target file: " + targetFile, e);
    }

    final ByteSource targetFileSource = com.google.common.io.Files.asByteSource(targetFile);
    return FileReference.builder()
      .setId(fileReferenceId)
      .setFileName(filename)
      .setContentsSource(targetFileSource)
      .build();
  }

  @Override
  public void delete(final long fileReferenceId, final String filename) throws FileStoreException {
    final Path targetPath = createTargetPath(fileReferenceId, filename);
    try {
      if (Files.deleteIfExists(targetPath)) {
        cleanupEmptyFolders(targetPath.getParent());
      }
    } catch (final IOException e) {
      throw new FileStoreException("Could not delete file: " + targetPath, e);
    }

  }

  private void cleanupEmptyFolders(final Path targetPath) {
    final Path root = config.getRoot();
    
    for(Path cleanPath = targetPath; cleanPath.startsWith(root) && !cleanPath.equals(root); cleanPath = cleanPath.getParent()) {
      try {
        final DirectoryStream<Path> directoryStream = Files.newDirectoryStream(targetPath);
        if (directoryStream.iterator()
          .hasNext()) {
          // directory not empty
          return;
        }

        Files.delete(targetPath);
        cleanPath = cleanPath.getParent();
      } catch (final Exception e) {
        // TODO log warning
        return;
      }
    }
  }

  private Path createTargetPath(final long fileReferenceId, final String filename) {
    final PathStrategy pathStrategy = config.getPathStrategy();
    return pathStrategy.createPath(config.getRoot(), fileReferenceId, filename);
  }

}
