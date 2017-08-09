package thirdpower.mydms.filestore.filesystem;

import java.nio.file.Path;

import thirdpower.mydms.filestore.filesystem.pathstrategies.PathStrategy;

public class FilesystemFileStoreConfiguration {

  private final Path root;
  private final PathStrategy pathStrategy;

  FilesystemFileStoreConfiguration(final Path root, final PathStrategy pathStrategy) {
    this.root = root;
    this.pathStrategy = pathStrategy;
  }

  public Path getRoot() {
    return root;
  }

  public PathStrategy getPathStrategy() {
    return pathStrategy;
  }


}
