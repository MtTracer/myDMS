package thirdpower.mydms.filestore.filesystem.pathstrategies;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.json.JsonObject;

import thirdpower.mydms.filestore.filesystem.FilesystemConfigJson;
import thirdpower.mydms.filestore.filesystem.pathstrategies.subfolderdistribution.SubfolderDistributionModule;

public class ConfiguredPathStrategyProvider implements Provider<PathStrategy> {

  private final Map<String, Provider<PathStrategy>> pathStrategies;
  private final JsonObject filesystemConfig;

  @Inject
  ConfiguredPathStrategyProvider(final Map<String, Provider<PathStrategy>> pathStrategies,
      @FilesystemConfigJson final JsonObject filesystemConfig) {
    this.pathStrategies = checkNotNull(pathStrategies);
    this.filesystemConfig = checkNotNull(filesystemConfig);
  }

  @Override
  public PathStrategy get() {
    final String pathStrategyName =
        filesystemConfig.getString("pathStrategy", SubfolderDistributionModule.STRATEGY_NAME);
    // TODO error if not found
    return pathStrategies.get(pathStrategyName)
      .get();
  }

}
