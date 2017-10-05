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

        val persistedRoot = catRepo.saveTree(rootCat)

        val categoryTree = catRepo.findTree()
        assertThat(categoryTree).isNotNull()
        assertThat(categoryTree).isSameAs(persistedRoot)

        assertThat(persistedRoot.id).isEqualTo(1L)
        assertThat(persistedRoot.name).isEqualTo("ROOT")

        assertThat(persistedRoot.children).containsExactly(childCat1, childCat2)
        assertThat(childCat1.parent).isSameAs(persistedRoot)
        assertThat(childCat2.parent).isSameAs(persistedRoot)
        assertThat(childCat2.children).containsExactly(childCat22, childCat21)
        assertThat(childCat21.parent).isSameAs(childCat2)
        assertThat(childCat22.parent).isSameAs(childCat2)

        val allCategories = em.createQuery("SELECT o FROM CategoryEntity o").resultList
        assertThat(allCategories).containsExactlyInAnyOrder(persistedRoot, childCat1, childCat2, childCat21, childCat22)

        val newRoot = CategoryEntity(id = 222L, name = "new dummy root", children = mutableListOf(childCat21, childCat22))
        val persistedNewRoot = catRepo.saveTree(newRoot)

        val newCategoryTree = catRepo.findTree()
        assertThat(newCategoryTree).isNotNull()
        assertThat(newCategoryTree).isSameAs(persistedNewRoot)

        assertThat(persistedNewRoot.id).isEqualTo(1L)
        assertThat(persistedNewRoot.name).isEqualTo("ROOT")

        assertThat(persistedNewRoot.children).containsExactly(childCat21, childCat22)
        assertThat(childCat21.parent).isSameAs(persistedNewRoot)
        assertThat(childCat21.parent).isSameAs(persistedNewRoot)

        val allNewCategories = em.createQuery("SELECT o FROM CategoryEntity o").resultList
        assertThat(allNewCategories).containsExactlyInAnyOrder(persistedNewRoot, childCat21, childCat22)

        persistService.stop()
    }
}