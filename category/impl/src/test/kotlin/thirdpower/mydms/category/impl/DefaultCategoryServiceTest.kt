package thirdpower.mydms.category.impl

import com.nhaarman.mockito_kotlin.*
import io.kotlintest.matchers.shouldBe
import io.kotlintest.matchers.shouldNotBe
import io.kotlintest.properties.Gen
import io.kotlintest.properties.forAll
import io.kotlintest.specs.BehaviorSpec
import org.mockito.invocation.InvocationOnMock
import thirdpower.mydms.category.api.Category
import thirdpower.mydms.category.persistence.CategoryEntity
import thirdpower.mydms.category.persistence.CategoryRepository
import thirdpower.mydms.utils.Tree
import thirdpower.mydms.utils.TreeNode


class DefaultCategoryServiceTest : BehaviorSpec() {

	private val treeGen = object : Gen<Tree<Category>> {
		override fun generate(): Tree<Category> {
			val maxDepth = Gen.choose(0, 5).generate()
			val childNumGen = Gen.choose(0, 10)
			return Tree(children = createChildNodes(depth = maxDepth, numberOfChildrenGen = childNumGen))
		}

		private fun createChildNodes(depth: Int, numberOfChildrenGen: Gen<Int>): List<TreeNode<Category>> {
			if (depth <= 0) {
				return emptyList()
			}

			val numChildren = numberOfChildrenGen.generate()
			return (1..numChildren).map {
				TreeNode(
						data = Category(name = Gen.string().generate()),
						children = createChildNodes(depth = depth - 1, numberOfChildrenGen = numberOfChildrenGen)
				)
			}
		}
	}

	private var nextId = 1L
	private fun CategoryEntity.withIds(): CategoryEntity {
		return CategoryEntity(
				id = this.id ?: nextId++,
				name = this.name,
				children = this.children.mapTo(mutableListOf()) { c -> c.withIds() }
		)
	}

	init {
		Given("empty category repository") {
			val repo = mock<CategoryRepository> {
				on { findRootsLazy() } doReturn emptyList<CategoryEntity>()
				on { findRootsEager() } doReturn emptyList<CategoryEntity>()

				argumentCaptor<CategoryEntity>().apply {
					on { saveCategoryTree(any()) } doAnswer { i: InvocationOnMock ->
						i.getArgument<CategoryEntity>(0)?.withIds()
					}
				}
			}
			val service = DefaultCategoryService(repo)

			When("tree is requested") {

				val tree = service.getCategoryTree()

				Then("empty tree should be returned") {
					tree shouldNotBe null
					tree.children shouldBe emptyList<TreeNode<Category>>()
				}
			}

			When("trees are saved") {

				Then("id and name of root node should be same") {
					forAll(treeGen, { tree: Tree<Category> ->
						val savedTree = service.saveCategoryTree(tree)
						savedTree.children.size == tree.children.size
					})
				}

			}
		}
	}
}