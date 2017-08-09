package thirdpower.mydms.filestore.filesystem.pathstrategies;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

public class SubfolderDistributionStrategyTest {

  @Test
  public void testCreateDirectoryPath() throws Exception {
    final SubfolderDistributionConfiguration config = new SubfolderDistributionConfiguration();
    final SubfolderDistributionStrategy strategy = new SubfolderDistributionStrategy(config);

    final Path root = Paths.get("c:", "someFolder", "myDMS", "datastore");
    final Path directoryPath1 = strategy.createDirectoryPath(root, 999, "someFilename.pdf");
    System.out.println(directoryPath1);
    final Path directoryPath2 = strategy.createDirectoryPath(root, 998, "someFilename.pdf");
    System.out.println(directoryPath2);
    final Path directoryPath3 = strategy.createDirectoryPath(root, 999, "asomeFilename.pdf");
    System.out.println(directoryPath3);
  }

}
