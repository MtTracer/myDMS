package thirdpower.mydms.filestore.filesystem;

import static com.google.common.base.Preconditions.checkNotNull;

import java.nio.file.Path;

import thirdpower.mydms.filestore.filesystem.pathstrategies.PathStrategy;

public class FilesystemFileStoreConfiguration {

  private final Path root;
  private final PathStrategy pathStrategy;

  FilesystemFileStoreConfiguration(final Path root, final PathStrategy pathStrategy) {
    this.root = checkNotNull(root);
    this.pathStrategy = checkNotNull(pathStrategy);
  }

  public Path getRoot() {
    return root;
  }

  public PathStrategy getPathStrategy() {
    return pathStrategy;
  }


}
