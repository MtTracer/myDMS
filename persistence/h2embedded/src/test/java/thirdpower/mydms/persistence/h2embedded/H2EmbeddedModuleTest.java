package thirdpower.mydms.persistence.h2embedded;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.persistence.EntityManager;

import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;

import thirdpower.mydms.persistence.api.PersistenceClassProvider;
import thirdpower.mydms.persistence.api.PersistenceClassRegistration;

public class H2EmbeddedModuleTest {

  @Test
  public void testJpa() throws IOException {
    final PersistenceClassProvider mockProvider = new PersistenceClassProvider() {
      @Override
      public void registerPersistenceClasses(final PersistenceClassRegistration registration) {
        registration.register(TestEntity.class);
      }
    };

    new DynamicPersistenceXml(ImmutableList.of(mockProvider))
      .setupPersistenceXml(Thread.currentThread(), Paths.get("target"));

    final Path dbDirectoryPath = Paths.get("target", "h2db-test");
    final Injector injector = Guice.createInjector(new H2EmbeddedModule(dbDirectoryPath));
    final PersistService persistService = injector.getInstance(PersistService.class);
    persistService.start();

    final EntityManager em = injector.getInstance(EntityManager.class);

    final TestEntity testEntity = new TestEntity();
    em.getTransaction()
      .begin();
    em.persist(testEntity);
    em.getTransaction()
      .commit();
    System.out.println(testEntity);
    em.detach(testEntity);

    final TestEntity testEntity2 = em.find(TestEntity.class, testEntity.getId());
    System.out.println(testEntity2);
  }
}
