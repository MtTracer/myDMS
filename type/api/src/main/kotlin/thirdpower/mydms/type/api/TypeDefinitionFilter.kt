package thirdpower.mydms.type.api

import thirdpower.mydms.utils.StringFilterMode

data class TypeDefinitionFilter(
        val nameFilter: String?,
        val nameFilterMode: StringFilterMode?
) {
    fun withNameFilter(nameFilter: String, nameFilterMode: StringFilterMode): TypeDefinitionFilter = copy(nameFilter = nameFilter, nameFilterMode = nameFilterMode)
}