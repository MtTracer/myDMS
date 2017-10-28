package thirdpower.mydms.category.rest

import thirdpower.mydms.category.api.Category
import thirdpower.mydms.category.api.CategoryService
import thirdpower.mydms.utils.Tree
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
        return CategoryPojo(
                id = null,
                name = "root",
                children = categoryRoot.children.map(this::toPojo))
    }

    @POST
    @Path("tree")
    fun saveCategoryTree(categoryRootPojo: CategoryPojo): CategoryPojo {
        val tree = Tree(children = categoryRootPojo.children.map(this::fromPojo))
        val savedCategoryTree = service.saveCategoryTree(tree)
        return CategoryPojo(
                id = null,
                name = "root",
                children = savedCategoryTree.children.map(this::toPojo))
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