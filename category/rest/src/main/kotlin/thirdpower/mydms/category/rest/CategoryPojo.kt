package thirdpower.mydms.category.rest

data class CategoryPojo(
        var id: Long?,
        var name: String,
        var children: List<CategoryPojo>
) {
    @Suppress("unused")
    private constructor() : this(id = null, name = "", children = listOf())
}