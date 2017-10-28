package thirdpower.mydms.category.persistence

import com.google.inject.Guice
import com.google.inject.persist.PersistService
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import thirdpower.mydms.persistence.h2embedded.H2EmbeddedModule
import javax.persistence.EntityManager

class CategoryEntityTest {

    @Test
    fun testPersist() {
        val injector = Guice.createInjector(H2EmbeddedModule("CategoryNodeEntityTest"))
        val persistService = injector.getInstance(PersistService::class.java)
        persistService.start()

        val em = injector.getInstance(EntityManager::class.java)

        val catRepo = injector.getInstance(CategoryRepository::class.java)

        val rootCat = CategoryEntity(id = 999L, name = "dummy root name")

        val childCat1 = CategoryEntity(name = "cat1")
        val childCat2 = CategoryEntity(name = "cat2")
        rootCat.children = mutableListOf(childCat1, childCat2)

        val childCat21 = CategoryEntity(name = "cat21")
        val childCat22 = CategoryEntity(name = "cat22")
        childCat2.children = mutableListOf(childCat22, childCat21)

        val persistedRoot = catRepo.saveCategoryTree(rootCat)

        val categoryTree = catRepo.findRootsEager()
        assertThat(categoryTree).isNotNull()
        assertThat(categoryTree).hasSize(1)
        val foundRoot = categoryTree.first()
        assertThat(foundRoot).isSameAs(persistedRoot)

        assertThat(persistedRoot.children)
                .extracting("name")
                .containsExactly("cat1", "cat2")

        val persistedCat1 = persistedRoot.children[0]
        assertThat(persistedCat1.parent).isSameAs(persistedRoot)

        val persistedCat2 = persistedRoot.children[1]
        assertThat(persistedCat2.parent).isSameAs(persistedRoot)

        assertThat(persistedCat2.children)
                .extracting("name")
                .containsExactly("cat22", "cat21")

        val persistedCat22 = persistedCat2.children[0]
        assertThat(persistedCat22.parent).isSameAs(persistedCat2)

        val persistedCat21 = persistedCat2.children[1]
        assertThat(persistedCat21.parent).isSameAs(persistedCat2)

        val allCategories = em.createQuery("SELECT o FROM CategoryEntity o").resultList
        assertThat(allCategories).extracting("name")
                .containsExactlyInAnyOrder("dummy root name", "cat1", "cat2", "cat21", "cat22")

        val newRoot = CategoryEntity(id = 999L, name = "another dummy root", children = mutableListOf(childCat21, childCat22))
        val persistedNewRoot = catRepo.saveCategoryTree(newRoot)

        val newCategoryTree = catRepo.findRootsEager()
        assertThat(newCategoryTree).isNotNull()
        assertThat(newCategoryTree).hasSize(1)
        val newFoundRoot = newCategoryTree.find { it.id == 999L }
        assertThat(newFoundRoot).isSameAs(persistedNewRoot)

        assertThat(persistedNewRoot.children)
                .extracting("name")
                .containsExactly("cat21", "cat22")
        val persistedNewCat21 = persistedNewRoot.children[0]
        assertThat(persistedNewCat21.parent).isSameAs(persistedNewRoot)
        val persistedNewCat22 = persistedNewRoot.children[1]
        assertThat(persistedNewCat22.parent).isSameAs(persistedNewRoot)

        val allNewCategories = em.createQuery("SELECT o FROM CategoryEntity o").resultList
        assertThat(allNewCategories).extracting("name")
                .containsExactlyInAnyOrder("another dummy root", "cat21", "cat22")

        persistService.stop()
    }
}