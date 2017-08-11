package thirdpower.mydms.filestore.filesystem.pathstrategies.subfolderdistribution;

import javax.json.Json;
import javax.json.JsonObject;

import com.google.common.hash.Hashing;
import com.google.common.io.BaseEncoding;

import thirdpower.mydms.filestore.filesystem.pathstrategies.subfolderdistribution.SubfolderDistributionConfiguration.Builder;

public class ConfigParser {

	ConfigParser() {
		
	}
	
	SubfolderDistributionConfiguration parse(JsonObject configJson) {
		Builder builder = SubfolderDistributionConfiguration.builder();
		JsonObject subfolderDistributionConfigJson = configJson.getJsonObject("subfolderDivision");
		if(null==subfolderDistributionConfigJson) {
			builder.build();
		}
		
		builder.setSubFolderLength(configJson.getInt("subFolderLength", 4));
		
		//TODO set hashfunction
		//TODO set encoding
		
		return builder.build();
	}
}
