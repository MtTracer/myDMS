package thirdpower.mydms.utils

interface CrudService<T, in ID, in FILTER> {
    fun findById(id: ID): T?

    fun findAll(filter: FILTER? = null): List<T>

    fun save(data: T): T

    fun delete(id: ID)
}