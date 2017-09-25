package thirdpower.mydms.type.api


data class PropertyDefinition(
        val id: Long,
        val name: String,
        val dataType: DataType)

interface DataTypeHandler {
    fun parseValue(valueStr: String): Any
    fun serializeValue(value: Any): String
}

data class DataType(
        val name: String,
        val unit: String?,
        val handler: DataTypeHandler
)