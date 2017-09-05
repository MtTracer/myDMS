package thirdpower.mydms.filestore.filesystem.pathstrategies;

import java.nio.file.Path;

public interface PathStrategy {
  public Path createPath(Path root, long id, String filename);
}
