package thirdpower.mydms.persistence.h2embedded;

import static com.google.common.base.Preconditions.checkNotNull;

import java.nio.file.Path;

import org.eclipse.persistence.config.PersistenceUnitProperties;

import com.google.common.collect.ImmutableMap;
import com.google.inject.AbstractModule;
import com.google.inject.persist.jpa.JpaPersistModule;

public class H2EmbeddedModule extends AbstractModule {

  private static final String DB_NAME = "myDms.h2";
  private final Path dbDirectoryPath;

  public H2EmbeddedModule(final Path dbDirectoryPath) {
    this.dbDirectoryPath = checkNotNull(dbDirectoryPath);
  }

  @Override
  protected void configure() {
    final Path dbPath = dbDirectoryPath.resolve(DB_NAME);
    final ImmutableMap<Object, Object> jpaProperties = ImmutableMap.<Object, Object>builder()
      .put(PersistenceUnitProperties.JDBC_URL.intern(), "jdbc:h2:" + dbPath.toAbsolutePath()
        .toString())
      .build();
    install(new JpaPersistModule("h2embedded").properties(jpaProperties));

  }

}
