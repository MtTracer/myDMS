package thirdpower.mydms.client.category

import thirdpower.mydms.client.rest.MyDmsRestApi
import tornadofx.Controller
import tornadofx.toModel

class CategoryController : Controller() {

    val api: MyDmsRestApi by inject()

    fun loadCategoryTree(): Category =
            api.get("categories/tree").one().toModel()

    fun saveCategoryTree(rootCategory: Category): Category =
            api.post("categories/tree", rootCategory).one().toModel()
}