package thirdpower.mydms.filestore.filesystem.pathstrategies;

import java.nio.file.Path;

public interface PathStrategy {
  public Path createDirectoryPath(Path root, long id, String filename);
}
