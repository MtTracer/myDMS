package thirdpower.mydms.persistence.h2embedded;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.floreysoft.jmte.Engine;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.Resources;

import thirdpower.mydms.persistence.api.PersistenceClassProvider;

public class DynamicPersistenceXml {


  private static final String DYNAMIC_CLASSPATH_NAME = "dynamicPersistenceClasspath";
  private static final String PERSISTENCE_XML_TEMPLATE = "persistence.xml.template";
  private final Iterable<PersistenceClassProvider> persistenceClassProviders;

  DynamicPersistenceXml(final Iterable<PersistenceClassProvider> persistenceClassProviders) {
    this.persistenceClassProviders = checkNotNull(persistenceClassProviders);
  }

  public void setupPersistenceXml(final Thread thread, final Path workDirectory)
      throws IOException {
    final List<String> persistenceClassNames = findPersistenceClassNames();
    final String persistenceXml = buildPersistenceXml(persistenceClassNames);
    final Path classPath = writePersistenceXml(workDirectory, persistenceXml);
    registerClasspath(thread, classPath);
  }

  private Path writePersistenceXml(final Path workDirectory, final String persistenceXml)
      throws IOException {

    final Path classpathFolder = workDirectory.resolve(DYNAMIC_CLASSPATH_NAME);
    final Path target = classpathFolder.resolve("META-INF")
      .resolve("persistence.xml");

    Files.createDirectories(target.getParent());
    Files.write(target, persistenceXml.getBytes(StandardCharsets.UTF_8));

    return classpathFolder;
  }

  private String buildPersistenceXml(final List<String> persistenceClassNames) throws IOException {
    final URL templateUrl = Resources.getResource(getClass(), PERSISTENCE_XML_TEMPLATE);
    final String template = Resources.toString(templateUrl, StandardCharsets.UTF_8);
    final Map<String, Object> model = ImmutableMap.of("persistenceClasses", persistenceClassNames);
    return Engine.createNonCachingEngine()
      .transform(template, model);
  }

  private List<String> findPersistenceClassNames() {
    final DefaultPersistenceClassRegistration registration =
        new DefaultPersistenceClassRegistration();
    persistenceClassProviders.forEach(p -> p.registerPersistenceClasses(registration));
    return registration.getPersistenceClasses()
      .stream()
      .map(Class::getCanonicalName)
      .collect(Collectors.toList());
  }

  private void registerClasspath(final Thread thread, final Path folder)
      throws MalformedURLException {

    final ClassLoader classLoader = thread.getContextClassLoader();
    final URLClassLoader newClassLoader = URLClassLoader.newInstance(new URL[] {folder.toUri()
      .toURL()}, classLoader);
    thread.setContextClassLoader(newClassLoader);
  }
}
