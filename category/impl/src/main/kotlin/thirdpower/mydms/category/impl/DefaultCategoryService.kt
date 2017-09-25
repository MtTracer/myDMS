package thirdpower.mydms.category.impl

import thirdpower.mydms.category.api.Category
import thirdpower.mydms.category.api.CategoryFilter
import thirdpower.mydms.category.api.CategoryService
import thirdpower.mydms.category.persistence.CategoryEntity
import thirdpower.mydms.category.persistence.CategoryRepository
import thirdpower.mydms.utils.TreeNode
import javax.inject.Inject


class DefaultCategoryService
@Inject constructor(
		private val repo : CategoryRepository 
) : CategoryService {
	
	override fun findById(id: Long): Category {
		return fromEntity(repo.findById(id))
	}

	override fun findAll(filter: CategoryFilter): List<Category> {
		return repo.findAll().map(this::fromEntity)
	}

	override fun getCategoryTree(): TreeNode<Category> {
		val roots = repo.findRoots().map(this::fromRootEntity)
		return TreeNode(data = Category(id = -1, name = "ROOT"), children = roots)
	}

	override fun save(category: Category): Category {
		val categoryEntity =
				if (category.id != null) {
					val persistentEntity = repo.findById(category.id) ?: throw IllegalArgumentException("Unknown category id ${category.id}")
					persistentEntity.apply {
						name = category.name
					}
				} else {
					toEntity(category)
				}


		repo.persist(categoryEntity)
		return fromEntity(categoryEntity)
	}

	override fun saveTree(categoryTree: TreeNode<Category>): TreeNode<Category> {
		val rootEntity = toRootEntity(categoryNode = categoryTree)
		repo.persist(rootEntity)
		return fromRootEntity(rootEntity)
	}

	override fun delete(id: Long) {
		val entity = repo.findById(id)
		repo.remove(entity)
	}

	private fun fromRootEntity(entity: CategoryEntity): TreeNode<Category> {
		val children = entity.children.map(this::fromRootEntity)
		val category = fromEntity(entity)
		return TreeNode(data = category, children = children)
	}

	private fun fromEntity(entity: CategoryEntity) : Category {
		return Category(
				id = entity.id,
				name = entity.name
		)
		
	}

	private fun toRootEntity(categoryNode: TreeNode<Category>, parent: CategoryEntity? = null): CategoryEntity {
		val entity = toEntity(categoryNode.data, parent)
		val children = categoryNode.children.map { toRootEntity(it, entity) }
		entity.children = children
		return entity
	}

	private fun toEntity(category: Category, parent: CategoryEntity? = null): CategoryEntity {
		return CategoryEntity(id = category.id, name = category.name, parent = parent)
	}
}