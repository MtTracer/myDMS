package thirdpower.mydms.filestore.filesystem.pathstrategies;

import static com.google.common.base.Preconditions.checkNotNull;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import javax.inject.Inject;

import com.google.common.base.Splitter;
import com.google.common.hash.HashFunction;

public class SubfolderDistributionStrategy implements PathStrategy {

  private final SubfolderDistributionConfiguration config;

  @Inject
  SubfolderDistributionStrategy(final SubfolderDistributionConfiguration config) {
    this.config = checkNotNull(config);
  }

  @Override
  public Path createDirectoryPath(final Path root, final long id, final String filename) {

    final byte[] hash = createHash(id, filename);
    final String path = encodePath(hash);
    final Path directory = createDirectoryPath(root, path);
    return directory.resolve(filename);
  }

  private byte[] createHash(final long id, final String filename) {
    final HashFunction hashFunction = config.getIdHashFunction();
    return hashFunction.newHasher()
      .putLong(id)
      .putString(filename, StandardCharsets.UTF_8)
      .hash()
      .asBytes();
  }

  private String encodePath(final byte[] hash) {
    return config.getHashEncoding()
      .encode(hash);
  }

  private Path createDirectoryPath(final Path root, final String subPath) {
    final int subFolderLength = config.getSubFolderLength();
    final Iterable<String> folders = Splitter.fixedLength(subFolderLength)
      .split(subPath);

    Path subFolderPath = root;
    for (final String folder : folders) {
      subFolderPath = subFolderPath.resolve(folder);
    }

    return subFolderPath;
  }

}
