package thirdpower.mydms.filestore.filesystem.pathstrategies.subfolderdistribution;

import com.google.common.testing.AbstractPackageSanityTests;

public class PackageSanityTest extends AbstractPackageSanityTests {

  public PackageSanityTest() {
    setDefault(ConfigParser.class, new ConfigParser());
    setDefault(SubfolderDistributionConfiguration.class,
        SubfolderDistributionConfiguration.builder()
          .build());
  }
}
