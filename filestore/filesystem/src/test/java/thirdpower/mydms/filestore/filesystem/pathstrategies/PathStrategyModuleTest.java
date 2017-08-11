package thirdpower.mydms.filestore.filesystem.pathstrategies;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import java.nio.file.Path;

import javax.json.Json;
import javax.json.JsonObject;

import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.multibindings.MapBinder;

import thirdpower.mydms.filestore.filesystem.FilesystemConfigJson;

public class PathStrategyModuleTest {

	@Test
	public void testInject() {

		PathStrategy testStratgy = new PathStrategy() {

			@Override
			public Path createDirectoryPath(Path root, long id, String filename) {
				return null;
			}
		};

		JsonObject configJson = Json.createObjectBuilder().add("pathStrategy", "testStrategy").build();
		Module testModule = new AbstractModule() {

			@Override
			protected void configure() {
				bind(JsonObject.class).annotatedWith(FilesystemConfigJson.class).toInstance(configJson);
				MapBinder.newMapBinder(binder(), String.class, PathStrategy.class).addBinding("testStrategy")
						.toInstance(testStratgy);

			}
		};
		Injector injector = Guice.createInjector(new PathStrategyModule(), testModule);
		PathStrategy pathStrategy = injector.getInstance(PathStrategy.class);
		assertThat(pathStrategy, sameInstance(testStratgy));
	}
}
