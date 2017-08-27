package thirdpower.mydms.filestore.filesystem;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.stream.JsonParser;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import thirdpower.mydms.filestore.api.FileStoreService;
import thirdpower.mydms.filestore.filesystem.pathstrategies.PathStrategy;

public class FilesystemModule extends AbstractModule {

  private final Path myDmsHome;

  public FilesystemModule(final Path myDmsHome) {
    this.myDmsHome = checkNotNull(myDmsHome);
  }

  @Override
  protected void configure() {
    bind(FileStoreService.class).to(FilesystemFileStoreService.class);
  }

  @Provides
  private FilesystemFileStoreConfiguration provideConfiguration(final PathStrategy strategy)
      throws IOException {
    final Path root = getFilestorePath();
    return new FilesystemFileStoreConfiguration(root, strategy);
  }

  @Provides
  @FilesystemConfigJson
  private JsonObject provideConfigJson() throws IOException {
    final Path root = getFilestorePath();

    final Path configFile = root.resolve("config.json");
    try (BufferedReader configReader = Files.newBufferedReader(configFile);
        JsonParser jsonParser = Json.createParser(configReader);) {
      jsonParser.next();
      return jsonParser.getObject();
    }
  }

  private Path getFilestorePath() {
    // TODO make GlobalConfig a JSONObject and read path
    return myDmsHome.resolve("myDmsFilestore");
  }
}
