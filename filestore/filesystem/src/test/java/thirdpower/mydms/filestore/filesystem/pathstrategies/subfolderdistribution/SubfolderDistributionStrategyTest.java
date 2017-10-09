package thirdpower.mydms.filestore.filesystem.pathstrategies.subfolderdistribution;

import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class SubfolderDistributionStrategyTest {

  @Test
  public void testCreateDirectoryPath() throws Exception {
    final SubfolderDistributionConfiguration config = SubfolderDistributionConfiguration.builder()
      .build();
    final SubfolderDistributionStrategy strategy = new SubfolderDistributionStrategy(config);
    final Path root = Files.createTempDirectory("SubfolderDistributionStrategyTest").resolve("datastore");
    
    final Path directoryPath1 = strategy.createPath(root, 999, "someFilename.pdf");
      assertThat(directoryPath1.normalize()).isEqualTo(root.resolve("gn_4/ePnJ/eIcm/fogD/E_fB/kw/999/someFilename.pdf").normalize());

    final Path directoryPath2 = strategy.createPath(root, 998, "someFilename.pdf");
      assertThat(directoryPath2.normalize()).isEqualTo(root.resolve("_GUr/EpPj/V08y/uVDr/MkWH/UA/998/someFilename.pdf").normalize());
  }

}
