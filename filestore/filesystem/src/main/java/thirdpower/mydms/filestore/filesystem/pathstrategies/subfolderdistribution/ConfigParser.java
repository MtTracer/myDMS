package thirdpower.mydms.filestore.filesystem.pathstrategies.subfolderdistribution;

import javax.inject.Inject;
import javax.json.JsonObject;

import thirdpower.mydms.filestore.filesystem.pathstrategies.subfolderdistribution.SubfolderDistributionConfiguration.Builder;

public class ConfigParser {

  @Inject
  ConfigParser() {

  }

  SubfolderDistributionConfiguration parse(final JsonObject configJson) {
    final Builder builder = SubfolderDistributionConfiguration.builder();
    final JsonObject subfolderDistributionConfigJson =
        configJson.getJsonObject("subfolderDivision");
    if (null == subfolderDistributionConfigJson) {
      builder.build();
    }

    builder.setSubFolderLength(configJson.getInt("subFolderLength", 4));

    // TODO set hashfunction
    // TODO set encoding

    return builder.build();
  }
}
