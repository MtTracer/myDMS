package thirdpower.mydms.category.impl

import com.google.common.graph.Graph
import thirdpower.mydms.category.api.Category
import thirdpower.mydms.category.api.CategoryFilter
import thirdpower.mydms.category.api.CategoryService

class DefaultCategoryService : CategoryService {
	override fun findById(id: Long): Category {
		TODO()
	}

	override fun findAll(filter: CategoryFilter): List<Category> {
		TODO()
	}

	override fun getCategoryTree(): Graph<Category> {
		TODO()
	}

	override fun save(category: Category): Category {
		TODO()
	}

	override fun saveTree(categoryTree: Graph<Category>): Graph<Category> {
		TODO()
	}

	override fun delete(categoryId: Long) {
		TODO()
	}
}