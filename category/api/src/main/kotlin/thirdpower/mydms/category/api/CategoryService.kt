package thirdpower.mydms.category.api

import thirdpower.mydms.utils.TreeNode

interface CategoryService {

    fun getCategoryTree(): TreeNode<Category>

    fun saveCategoryTree(categoryTree: TreeNode<Category>): TreeNode<Category>

}