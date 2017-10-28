package thirdpower.mydms.category.api

import thirdpower.mydms.utils.Tree

interface CategoryService {

    fun getCategoryTree(): Tree<Category>

    fun saveCategoryTree(categoryTree: Tree<Category>): Tree<Category>

}