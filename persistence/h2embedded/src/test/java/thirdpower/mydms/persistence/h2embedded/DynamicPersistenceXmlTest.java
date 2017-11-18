package thirdpower.mydms.persistence.h2embedded;

import com.google.common.collect.ImmutableList;
import com.google.common.io.Resources;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import thirdpower.mydms.persistence.api.PersistenceClassProvider;
import thirdpower.mydms.persistence.api.PersistenceClassRegistration;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;

public class DynamicPersistenceXmlTest {

  private static ClassLoader classLoader;

  @BeforeClass
  public static void init() {
    classLoader = Thread.currentThread()
      .getContextClassLoader();
  }

  @AfterClass
  public static void cleanUp() {
    Thread.currentThread()
      .setContextClassLoader(classLoader);
  }

  @Test
  public void testSetupPersistenceXml() throws Exception {
    final PersistenceClassProvider mockProvider = new PersistenceClassProvider() {
      @Override
      public void registerPersistenceClasses(final PersistenceClassRegistration registration) {
        registration.register(TestEntity.class)
          .register(AnotherEntity.class);
      }
    };

    final DynamicPersistenceXml dynamicPersistenceXml =
        new DynamicPersistenceXml(ImmutableList.of(mockProvider));
    dynamicPersistenceXml.setupPersistenceXml(Thread.currentThread(),
        Files.createTempDirectory("DynamicPersistenceXmlTest"));

    final URL persistenceXml = Resources.getResource("META-INF/persistence.xml");
    final String contents = Resources.toString(persistenceXml, StandardCharsets.UTF_8);

    final URL expectedPersistenceXml =
        Resources.getResource(getClass(), "expected_persistence.xml");
    final String expectedContents =
        Resources.toString(expectedPersistenceXml, StandardCharsets.UTF_8);

      assertThat(contents).isXmlEqualTo(expectedContents);
  }

}
