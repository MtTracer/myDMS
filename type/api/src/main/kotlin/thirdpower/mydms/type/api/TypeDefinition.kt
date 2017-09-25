package thirdpower.mydms.type.api

data class TypeDefinition(
        val id: Long,
        val name: String,
        val properties: List<PropertyDefinition>
)