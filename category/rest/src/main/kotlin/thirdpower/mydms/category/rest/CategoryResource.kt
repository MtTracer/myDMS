package thirdpower.mydms.category.rest

import thirdpower.mydms.category.api.Category
import thirdpower.mydms.category.api.CategoryService
import thirdpower.mydms.utils.TreeNode
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("categories")
class CategoryResource
@Inject constructor(private val service: CategoryService) {

    @GET
    @Path("tree")
    fun getCategoryTree(): CategoryPojo {
        val categoryRoot = service.getCategoryTree()
        return toPojo(categoryRoot)
    }

    @POST
    @Path("tree")
    fun saveCategoryTree(categoryRootPojo: CategoryPojo): CategoryPojo {
        val categoryRoot = fromPojo(categoryRootPojo)
        val savedCategoryTree = service.saveCategoryTree(categoryRoot)
        return toPojo(savedCategoryTree)
    }

    private fun fromPojo(pojo: CategoryPojo): TreeNode<Category> {
        return TreeNode(data = Category(
                id = pojo.id,
                name = pojo.name
        ), children = pojo.children.map(this::fromPojo))
    }

    private fun toPojo(category: TreeNode<Category>): CategoryPojo {
        return CategoryPojo(
                id = category.data.id,
                name = category.data.name,
                children = category.children.map(this::toPojo)
        )
    }
}