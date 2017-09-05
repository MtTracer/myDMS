package thirdpower.mydms.filestore.filesystem.pathstrategies.subfolderdistribution;

import javax.inject.Inject;
import javax.json.JsonObject;

import thirdpower.mydms.filestore.filesystem.pathstrategies.subfolderdistribution.SubfolderDistributionConfiguration.Builder;

public class ConfigParser {

  private static final String CONFIG_NAME = "subfolderDistribution";
  private static final String CONFIG_SUBFOLDER_LENGTH = "subFolderLength";

  @Inject
  ConfigParser() {

  }

  SubfolderDistributionConfiguration parse(final JsonObject configJson) {
    final Builder builder = SubfolderDistributionConfiguration.builder();
    final JsonObject subfolderDistributionConfigJson =
        configJson.getJsonObject(CONFIG_NAME);
    if (null == subfolderDistributionConfigJson) {
      builder.build();
    }

    builder.setSubFolderLength(configJson.getInt(CONFIG_SUBFOLDER_LENGTH, 4));

    // TODO set hashfunction
    // TODO set encoding

    return builder.build();
  }
}
