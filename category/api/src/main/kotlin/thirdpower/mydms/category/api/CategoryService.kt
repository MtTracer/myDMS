package thirdpower.mydms.category.api

import com.google.common.graph.Graph

interface CategoryService {
	
	fun findById(id: Long) : Category;
	
	fun findAll(filter: CategoryFilter) : List<Category>;
	
	fun getCategoryTree() : Graph<Category>;
	
	fun save(parentId: Long, category: Category): Category;
	
	fun saveTree(categoryTree: Graph<Category>) : Graph<Category>;
	
	fun delete(categoryId: Long);
}