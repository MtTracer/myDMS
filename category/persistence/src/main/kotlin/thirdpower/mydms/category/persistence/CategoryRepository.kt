package thirdpower.mydms.category.persistence

import javax.inject.Inject
import javax.persistence.EntityManager

class CategoryRepository @Inject constructor(
        private val entityManager: EntityManager
) {

    fun findTree(): CategoryEntity? {
        //query all to fill cache
        entityManager.createQuery("select c from CategoryEntity c left join fetch c.children").resultList
        return findRoot()
    }

    fun saveTree(treeRoot: CategoryEntity): CategoryEntity {
        //use existing root or create new one with children from given treeRoot
        val root = findRoot()?.apply {
            children = treeRoot.children
        } ?: CategoryEntity(id = null, name = "ROOT", children = treeRoot.children)

        entityManager.transaction.begin()
        entityManager.persist(root)
        entityManager.transaction.commit()

        //FIXME necessary because first persist removes all children of no more existing categories even if they are now children in new tree under another category
        entityManager.transaction.begin()
        entityManager.persist(root)
        entityManager.transaction.commit()

        return root
    }

    private fun findRoot(): CategoryEntity? {
        val cb = entityManager.criteriaBuilder
        val q = cb.createQuery(CategoryEntity::class.java)
		val criteria = q.from(CategoryEntity::class.java)
        val select = q.select(criteria).where(cb.isNull(criteria.get<CategoryEntity>("parent")))
        return entityManager.createQuery(select).resultList.firstOrNull()
    }

}