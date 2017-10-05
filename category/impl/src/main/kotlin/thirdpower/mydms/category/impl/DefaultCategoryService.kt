package thirdpower.mydms.category.impl

import thirdpower.mydms.category.api.Category
import thirdpower.mydms.category.api.CategoryService
import thirdpower.mydms.category.persistence.CategoryEntity
import thirdpower.mydms.category.persistence.CategoryRepository
import thirdpower.mydms.utils.TreeNode
import javax.inject.Inject


class DefaultCategoryService
@Inject constructor(
		private val repo : CategoryRepository 
) : CategoryService {

    override fun getCategoryTree(): TreeNode<Category> =
            fromEntity(repo.findTree())

    override fun saveCategoryTree(categoryTree: TreeNode<Category>): TreeNode<Category> {
        val dummyRoot = TreeNode(data = Category(id = null, name = "ROOT"), children = categoryTree.children)
        val persistedRoot = repo.saveTree(toEntity(dummyRoot))
        return fromEntity(persistedRoot)
    }

    private fun fromEntity(entity: CategoryEntity?): TreeNode<Category> =
            if (null == entity) {
                TreeNode(data = Category(id = null, name = "ROOT"))
            } else {
                TreeNode(
                        data = Category(
                                id = entity.id,
                                name = entity.name),
                        children = entity.children.map(this::fromEntity)
                )
            }

    private fun toEntity(categoryNode: TreeNode<Category>): CategoryEntity =
            CategoryEntity(
                    id = categoryNode.data.id,
                    name = categoryNode.data.name,
                    children = categoryNode.children.mapTo(mutableListOf()) { toEntity(it) }
            )

}