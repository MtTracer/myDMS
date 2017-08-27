package thirdpower.mydms.filestore.filesystem;

import static org.mockito.Mockito.mock;

import com.google.common.testing.AbstractPackageSanityTests;

import thirdpower.mydms.config.GlobalConfig;

public class PackageSanityTest extends AbstractPackageSanityTests {

  public PackageSanityTest() {
    setDefault(FilesystemFileStoreConfiguration.class,
        mock(FilesystemFileStoreConfiguration.class));
    setDefault(GlobalConfig.class, mock(GlobalConfig.class));
  }
}
