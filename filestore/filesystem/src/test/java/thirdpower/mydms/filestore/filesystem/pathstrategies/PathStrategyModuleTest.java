package thirdpower.mydms.filestore.filesystem.pathstrategies;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Path;

import javax.json.Json;
import javax.json.JsonObject;

import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.multibindings.MapBinder;

import thirdpower.mydms.filestore.filesystem.FilesystemConfigJson;

public class PathStrategyModuleTest {

  @Test
  public void testInject() {

    final PathStrategy testStratgy = new PathStrategy() {

      @Override
      public Path createDirectoryPath(final Path root, final long id, final String filename) {
        return null;
      }
    };

    final JsonObject configJson = Json.createObjectBuilder()
      .add("pathStrategy", "testStrategy")
      .build();
    final Module testModule = new AbstractModule() {

      @Override
      protected void configure() {
        bind(JsonObject.class).annotatedWith(FilesystemConfigJson.class)
          .toInstance(configJson);
        MapBinder.newMapBinder(binder(), String.class, PathStrategy.class)
          .addBinding("testStrategy")
          .toInstance(testStratgy);

      }
    };
    final Injector injector = Guice.createInjector(new PathStrategyModule(), testModule);
    final PathStrategy pathStrategy = injector.getInstance(PathStrategy.class);
    assertThat(pathStrategy).isSameAs(testStratgy);
  }
}
