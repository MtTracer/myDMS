package thirdpower.mydms.category.api

import thirdpower.mydms.utils.StringFilterMode

data class CategoryFilter(
		val nameFilter: String?,
		val nameFilterMode: StringFilterMode?
) {
	fun withNameFilter(nameFilter: String, nameFilterMode: StringFilterMode): CategoryFilter = copy(nameFilter = nameFilter, nameFilterMode = nameFilterMode)
}