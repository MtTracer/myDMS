package thirdpower.mydms.filestore.filesystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.stream.JsonParser;

import com.google.auto.service.AutoService;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Provides;

import thirdpower.mydms.filestore.api.FileStoreService;
import thirdpower.mydms.filestore.filesystem.pathstrategies.PathStrategy;

@AutoService(Module.class)
public class FilesystemModule extends AbstractModule {
 
	@Override
	protected void configure() {
		bind(FileStoreService.class).to(FilesystemFileStoreService.class);
	}

	@Provides
	private FilesystemFileStoreConfiguration provideConfiguration(final PathStrategy strategy) throws IOException {
		Path root = getDummyFilestorePath();
		return new FilesystemFileStoreConfiguration(root, strategy);
	}

	@Provides
	@FilesystemConfigJson
	private JsonObject provideConfigJson() throws IOException {
		Path root = getDummyFilestorePath();
				
		Path configFile = root.resolve("config.json");
		try (BufferedReader configReader = Files.newBufferedReader(configFile);
			JsonParser jsonParser = Json.createParser(configReader);) {
			return jsonParser.getObject();
		}
	}

	private Path getDummyFilestorePath() {
		// TODO replace by configured store
		return Paths.get("d:", "dev", "myDmsFilestore");

	}
}
