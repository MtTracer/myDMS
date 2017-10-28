package thirdpower.mydms.category.persistence

import javax.persistence.*

@Entity
@Table(name="CATEGORY")
class CategoryEntity(
		id: Long? = null,
		name: String = "",
		children: List<CategoryEntity> = emptyList()
) {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	var id: Long? = id

	@Column(name = "NAME")
	var name: String = name

    @ManyToOne(optional = true, fetch = FetchType.LAZY, cascade = arrayOf(CascadeType.ALL))
    @JoinColumn(name = "PARENT_ID")
    var parent: CategoryEntity? = null
        private set

	@OneToMany(mappedBy = "parent", orphanRemoval = true, fetch = FetchType.LAZY, cascade = arrayOf(CascadeType.ALL))
	@OrderColumn(name = "position")
	var children: MutableList<CategoryEntity> = resetParent(children)
        get() = field.toMutableList()
        set(children) {
	        field = resetParent(children)
        }

	private fun resetParent(children: List<CategoryEntity>): MutableList<CategoryEntity> =
			children.mapTo(mutableListOf()) { c ->
				c.parent?.removeChild(c)
				c.parent = this
				c
			}

    private fun removeChild(child: CategoryEntity) {
        children.remove(child)
        child.parent = null
    }

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false

		other as CategoryEntity

		if (id != other.id) return false
		if (name != other.name) return false
		if (parent != other.parent) return false

		return true
	}

	override fun hashCode(): Int {
		var result = id?.hashCode() ?: 0
		result = 31 * result + name.hashCode()
		result = 31 * result + (parent?.hashCode() ?: 0)
		return result
	}

	override fun toString(): String {
		return "CategoryEntity(id=$id, name='$name', children='$children')"
	}


}