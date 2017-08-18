package thirdpower.mydms.persistence.h2embedded;

import static org.mockito.Mockito.mock;

import com.google.common.testing.AbstractPackageSanityTests;

import thirdpower.mydms.config.GlobalConfig;

public class PackageSanityTests extends AbstractPackageSanityTests {

  public PackageSanityTests() {
    setDefault(GlobalConfig.class, mock(GlobalConfig.class));
  }
}
