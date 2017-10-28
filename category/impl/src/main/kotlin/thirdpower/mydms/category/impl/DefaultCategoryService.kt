package thirdpower.mydms.category.impl

import thirdpower.mydms.category.api.Category
import thirdpower.mydms.category.api.CategoryService
import thirdpower.mydms.category.persistence.CategoryEntity
import thirdpower.mydms.category.persistence.CategoryRepository
import thirdpower.mydms.utils.Tree
import thirdpower.mydms.utils.TreeNode
import javax.inject.Inject


class DefaultCategoryService
@Inject constructor(
		private val repo : CategoryRepository 
) : CategoryService {

	override fun getCategoryTree(): Tree<Category> {
		val root = repo.findRootsEager().firstOrNull()
		val rootChildren = root?.children?.map(this::fromEntity)
				?: emptyList()
		return Tree(children = rootChildren)
	}

	override fun saveCategoryTree(categoryTree: Tree<Category>): Tree<Category> {
		val childEntities = categoryTree.children.mapTo(mutableListOf(), this::toEntity)
		val root = repo.findRootsLazy().firstOrNull()?.apply { children = childEntities }
				?: CategoryEntity(name = "ROOT", children = childEntities)
		val persistedRoot = repo.saveCategoryTree(root)
		return Tree(children = persistedRoot.children.map(this::fromEntity))
    }

	private fun fromEntity(entity: CategoryEntity): TreeNode<Category> =
			TreeNode(
					data = Category(
							id = entity.id,
							name = entity.name),
					children = entity.children.map(this::fromEntity)
			)

    private fun toEntity(categoryNode: TreeNode<Category>): CategoryEntity =
            CategoryEntity(
                    id = categoryNode.data.id,
                    name = categoryNode.data.name,
                    children = categoryNode.children.mapTo(mutableListOf()) { toEntity(it) }
            )

}