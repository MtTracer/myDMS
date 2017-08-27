package thirdpower.mydms.config;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.stream.JsonParser;

import com.google.common.base.Strings;

public class GlobalConfigFactory {

  private static final String DB_PATH_KEY = "dbPath";
  private static final String DEFAULT_DB_PATH = "db";
  private static final String GLOBAL_CONFIG_FILE = "myDMS.json";
  private static final String GLOBAL_CONFIG_HOME_PROPERTY = "MYDMS_HOME";

  public static GlobalConfig load() {
    final Path configFilePath = getConfigFilePath();
    final Path myDmsHome = configFilePath.getParent();
    if (!configFilePath.toFile()
      .isFile()) {
      return parseConfig(myDmsHome, Json.createObjectBuilder()
        .build());
    }
    try (BufferedReader configReader = Files.newBufferedReader(configFilePath);
        JsonParser configParser = Json.createParser(configReader)) {
      configParser.next();
      final JsonObject jsonConfig = configParser.getObject();
      return parseConfig(myDmsHome, jsonConfig);
    } catch (final Exception e) {
      // TODO handle error
      throw new Error("Could not read global config.", e);
    }
  }

  private static Path getConfigFilePath() {
    String home = System.getProperty(GLOBAL_CONFIG_HOME_PROPERTY);

    if (Strings.isNullOrEmpty(home)) {
      home = System.getenv(GLOBAL_CONFIG_HOME_PROPERTY);
    }

    if (Strings.isNullOrEmpty(home)) {
      return Paths.get(GLOBAL_CONFIG_FILE);
    } else {
      return Paths.get(home, GLOBAL_CONFIG_FILE);
    }
  }

  private static GlobalConfig parseConfig(final Path home, final JsonObject jsonConfig) {
    final String myDmsDbStr = jsonConfig.getString(DB_PATH_KEY, DEFAULT_DB_PATH);
    final Path myDmsDb = home.resolve(myDmsDbStr);
    return new GlobalConfig(home, myDmsDb);
  }

  private GlobalConfigFactory() {

  }


}
