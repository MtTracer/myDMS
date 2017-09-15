package thirdpower.mydms.category.persistence

import thirdpower.mydms.persistence.api.AbstractRepository

class CategoryRepository : AbstractRepository<CategoryEntity, Long>() {
	
	fun findRoots() : List<CategoryEntity> {
		val em = getEntityManager();
		em.createQuery("select c from CategoryEntity c left join fetch c.children").getResultList();
		val cb = em.getCriteriaBuilder();
		val q = cb.createQuery(CategoryEntity::class.java);
		val criteria = q.from(CategoryEntity::class.java)
		val select = q.select(criteria).where(cb.isNull(criteria.get<CategoryEntity>("parent") ));
		return em.createQuery(select).getResultList();
	}
}