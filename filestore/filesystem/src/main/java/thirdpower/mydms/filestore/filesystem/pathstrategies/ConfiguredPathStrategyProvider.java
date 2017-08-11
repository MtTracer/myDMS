package thirdpower.mydms.filestore.filesystem.pathstrategies;

import java.util.Map;

import javax.inject.Provider;
import javax.json.JsonObject;

import thirdpower.mydms.filestore.filesystem.FilesystemConfigJson;
import thirdpower.mydms.filestore.filesystem.pathstrategies.subfolderdistribution.SubfolderDistributionModule;

public class ConfiguredPathStrategyProvider implements Provider<PathStrategy> {

	private Map<String, Provider<PathStrategy>> pathStrategies;
	private JsonObject filesystemConfig;

	ConfiguredPathStrategyProvider(Map<String, Provider<PathStrategy>> pathStrategies,
			@FilesystemConfigJson JsonObject filesystemConfig) {
		this.pathStrategies = pathStrategies;
		this.filesystemConfig = filesystemConfig;
	}

	@Override
	public PathStrategy get() {
		String pathStrategyName = filesystemConfig.getString("pathStrategy", SubfolderDistributionModule.STRATEGY_NAME);
		// TODO error if not found
		return pathStrategies.get(pathStrategyName).get();
	}

}
