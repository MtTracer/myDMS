package thirdpower.mydms.filestore.filesystem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.auto.service.AutoService;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Provides;

import thirdpower.mydms.filestore.api.FileStoreService;
import thirdpower.mydms.filestore.filesystem.pathstrategies.SubfolderDistributionStrategy;


@AutoService(Module.class)
public class FilesystemModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(FileStoreService.class).to(FilesystemFileStoreService.class);
  }

  // TODO read config from file
  @Provides
  private FilesystemFileStoreConfiguration provideConfiguration(
      final SubfolderDistributionStrategy strategy) throws IOException {
    final Path tmpDir = Files.createTempDirectory("myDmsFileStore");
    return new FilesystemFileStoreConfiguration(tmpDir, strategy);
  }

}
