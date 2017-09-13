package thirdpower.mydms.category.persistence

import javax.persistence.Persistence
import com.google.inject.Guice
import java.nio.file.Path
import java.nio.file.Files
import thirdpower.mydms.persistence.h2embedded.H2EmbeddedModule
import org.eclipse.persistence.jpa.rs.PersistenceContext
import javax.persistence.EntityManager
import org.junit.Test
import javax.persistence.EntityManagerFactory
import com.google.inject.persist.PersistService

class CategoryNodeEntityTest {
	
	@Test
	fun testPersist() {
		var injector = Guice.createInjector(H2EmbeddedModule("CategoryNodeEntityTest"));
		
		var persistService = injector.getInstance(PersistService::class.java);
		persistService.start();
		
		var entityManagerFactory = injector.getInstance(EntityManagerFactory::class.java);
		var entityManager = entityManagerFactory.createEntityManager();
		
		var rootCat = CategoryEntity(name="root");
		var childCat1 = CategoryEntity(name="c1");
		var childCat2 = CategoryEntity(name="c2");
		var childCat21 = CategoryEntity(name="c21");
		var childCat22 = CategoryEntity(name="c22");
		
		entityManager.getTransaction().begin();
		entityManager.persist(rootCat);
		entityManager.persist(childCat1);
		entityManager.persist(childCat2);
		entityManager.persist(childCat21);
		entityManager.persist(childCat22);
		entityManager.getTransaction().commit();
		
		persistService.stop();
	}
}