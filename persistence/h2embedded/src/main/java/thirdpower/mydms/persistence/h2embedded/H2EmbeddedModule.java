package thirdpower.mydms.persistence.h2embedded;

import com.google.common.collect.ImmutableMap;
import com.google.inject.AbstractModule;
import com.google.inject.persist.jpa.JpaPersistModule;
import org.eclipse.persistence.config.PersistenceUnitProperties;

import java.nio.file.Path;

import static com.google.common.base.Preconditions.checkNotNull;

public class H2EmbeddedModule extends AbstractModule {

  private static final String DB_NAME = "myDms.h2";
  private final String dbUrlSuffix;

  public H2EmbeddedModule(final Path dbDirectoryPath) {
    this.dbUrlSuffix = checkNotNull(dbDirectoryPath).resolve(DB_NAME).toAbsolutePath().toString();
  }
  
  public H2EmbeddedModule(String memDbName) {
    this.dbUrlSuffix = "mem:" + checkNotNull(memDbName);
  }

  @Override
  protected void configure() {
      final ImmutableMap<Object, Object> jpaProperties = ImmutableMap.builder()
      .put(PersistenceUnitProperties.JDBC_URL.intern(), "jdbc:h2:" + dbUrlSuffix)
      .build();
    install(new JpaPersistModule("h2embedded").properties(jpaProperties));

  }

}
