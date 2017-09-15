package thirdpower.mydms.category.impl

import com.google.common.graph.Graph
import thirdpower.mydms.category.api.Category
import thirdpower.mydms.category.api.CategoryFilter
import thirdpower.mydms.category.api.CategoryService
import thirdpower.mydms.category.persistence.CategoryRepository
import javax.inject.Inject
import thirdpower.mydms.category.persistence.CategoryEntity
import com.google.common.graph.GraphBuilder
import com.google.common.graph.MutableGraph


class DefaultCategoryService
@Inject constructor(
		private val repo : CategoryRepository 
) : CategoryService {
	
	override fun findById(id: Long): Category {
		return fromEntity(repo.findById(id));
	}

	override fun findAll(filter: CategoryFilter): List<Category> {
		return repo.findAll().map(this::fromEntity);
	}

	override fun getCategoryTree(): Graph<Category> {
		TODO()
	}

	override fun save(parentId: Long, category: Category): Category {
		val parent = repo.findById(parentId);
		val entity = toEntity(category, parent);
		repo.persist(entity);
		return fromEntity(entity);
	}

	override fun saveTree(categoryTree: Graph<Category>): Graph<Category> {
		TODO()
	}

	override fun delete(categoryId: Long) {
		val entity = repo.findById(categoryId);
		repo.remove(entity);
	}
	
	private fun fromEntity(entity: CategoryEntity) : Category {
		return Category(
				id = entity.id,
				name = entity.name
		);
		
	}
	
	private fun toEntity(category: Category, parent : CategoryEntity?) : CategoryEntity {
		return CategoryEntity(id = category.id, name = category.name, parent = parent)
	}
}