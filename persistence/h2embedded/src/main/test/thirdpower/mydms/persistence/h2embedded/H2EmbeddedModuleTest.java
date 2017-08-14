package thirdpower.mydms.persistence.h2embedded;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.persistence.EntityManager;

import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class H2EmbeddedModuleTest {

  @Test
  public void testJpa() {
    final Path dbDirectoryPath = Paths.get("target", "h2db-test");
    final Injector injector = Guice.createInjector(new H2EmbeddedModule(dbDirectoryPath));
    final EntityManager em = injector.getInstance(EntityManager.class);

    final TestEntity testEntity = new TestEntity("huhu");
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
