package thirdpower.mydms.category.api

data class CategoryFilter(val name: String? = null, val nameFilterMode: NameFilterMode? = null) {

	fun withNameFilter(nameFilter: String, filterMode: NameFilterMode): CategoryFilter =
			copy(name = nameFilter, nameFilterMode = filterMode)

	enum class NameFilterMode {
		EQUALS, CONTAINS, REGEX
	}
}