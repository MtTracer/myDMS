package thirdpower.mydms.filestore.filesystem;

import static org.mockito.Mockito.mock;

import com.google.common.testing.AbstractPackageSanityTests;

public class PackageSanityTest extends AbstractPackageSanityTests {

  public PackageSanityTest() {
    setDefault(FilesystemFileStoreConfiguration.class,
        mock(FilesystemFileStoreConfiguration.class));
  }
}
