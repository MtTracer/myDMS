package thirdpower.mydms.filestore.filesystem.pathstrategies;

import java.nio.file.Path;

public interface PathStrategy {
    Path createPath(Path root, long id, String filename);
}
