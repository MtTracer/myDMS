package thirdpower.mydms.config;

import static com.google.common.base.Preconditions.checkNotNull;

import java.nio.file.Path;

public class GlobalConfig {

  private final Path myDmsHome;
  private final Path myDmsDb;

  GlobalConfig(final Path myDmsHome, final Path myDmsDb) {
    this.myDmsHome = checkNotNull(myDmsHome);
    this.myDmsDb = checkNotNull(myDmsDb);
  }

  public Path getMyDmsHome() {
    return myDmsHome;
  }

  public Path getMyDmsDb() {
    return myDmsDb;
  }
}
