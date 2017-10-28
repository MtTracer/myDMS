package thirdpower.mydms.category.persistence

import javax.inject.Inject
import javax.persistence.EntityManager

class CategoryRepository @Inject constructor(
        private val entityManager: EntityManager
) {

	fun findRootsEager(): List<CategoryEntity> {
        //query all to fill cache
        entityManager.createQuery("select c from CategoryEntity c left join fetch c.children").resultList
		return findRootsLazy()
    }

	fun saveCategoryTree(categoryEntity: CategoryEntity): CategoryEntity {
		entityManager.transaction.begin()

		val persistedEntity = recursiveMerge(categoryEntity)

		entityManager.persist(persistedEntity)
        entityManager.transaction.commit()

		return persistedEntity
    }

	private fun recursiveMerge(entity: CategoryEntity): CategoryEntity =
			if (entity.id == null) {
				//new entity must not be merged, but maybe children are already existent
				entity.apply {
					children = children.mapTo(mutableListOf()) { c ->
						recursiveMerge(c)
					}
				}
			} else {
				//children are merged recursively by CascadeType.ALL in CategoryEntity
				entityManager.merge(entity)
			}

	fun findRootsLazy(): List<CategoryEntity> {
        val cb = entityManager.criteriaBuilder
        val q = cb.createQuery(CategoryEntity::class.java)
		val criteria = q.from(CategoryEntity::class.java)
        val select = q.select(criteria).where(cb.isNull(criteria.get<CategoryEntity>("parent")))
		return entityManager.createQuery(select).resultList
    }

}