package thirdpower.mydms.category.api

import thirdpower.mydms.utils.CrudService
import thirdpower.mydms.utils.TreeNode

interface CategoryService : CrudService<Category, Long, CategoryFilter> {

    fun getCategoryTree(): TreeNode<Category>

    fun saveTree(categoryTree: TreeNode<Category>): TreeNode<Category>

}