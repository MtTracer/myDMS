package thirdpower.mydms.filestore.filesystem.pathstrategies.subfolderdistribution;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.json.JsonObject;

import thirdpower.mydms.filestore.filesystem.FilesystemConfigJson;

public class ParsedSubfolderDistributionConfigProvider
    implements Provider<SubfolderDistributionConfiguration> {

  private final ConfigParser parser;
  private final JsonObject filesystemConfig;

  @Inject
  ParsedSubfolderDistributionConfigProvider(final ConfigParser parser,
      @FilesystemConfigJson final JsonObject filesystemConfig) {
    this.parser = checkNotNull(parser);
    this.filesystemConfig = checkNotNull(filesystemConfig);
  }

  @Override
  public SubfolderDistributionConfiguration get() {
    return parser.parse(filesystemConfig);
  }

}
