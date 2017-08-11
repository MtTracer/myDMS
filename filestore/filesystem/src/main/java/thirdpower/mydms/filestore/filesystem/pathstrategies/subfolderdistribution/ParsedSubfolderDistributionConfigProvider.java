package thirdpower.mydms.filestore.filesystem.pathstrategies.subfolderdistribution;

import javax.inject.Provider;
import javax.json.JsonObject;

import thirdpower.mydms.filestore.filesystem.FilesystemConfigJson;

public class ParsedSubfolderDistributionConfigProvider implements Provider<SubfolderDistributionConfiguration> {

	private ConfigParser parser;
	private JsonObject filesystemConfig;

	public ParsedSubfolderDistributionConfigProvider(ConfigParser parser,
			@FilesystemConfigJson JsonObject filesystemConfig) {
		this.parser = parser;
		this.filesystemConfig = filesystemConfig;
	}

	@Override
	public SubfolderDistributionConfiguration get() {
		return parser.parse(filesystemConfig);
	}

}
