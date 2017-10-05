package thirdpower.mydms.category.persistence

import javax.persistence.*

@Entity
@Table(name="CATEGORY")
data class CategoryEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
        val id: Long? = null,

        @Column(name = "NAME")
        val name: String
) {

    constructor() : this(id = null, name = "")
    constructor(id: Long? = null, name: String, children: Collection<CategoryEntity> = emptyList()) : this(id, name) {
        this.children = children.toMutableList()
        children.forEach { c ->
            c.parent?.removeChild(c)
            c.parent = this
        }
    }

    @ManyToOne(optional = true, fetch = FetchType.LAZY, cascade = arrayOf(CascadeType.ALL))
    @JoinColumn(name = "PARENT_ID")
    var parent: CategoryEntity? = null
        private set

	@OneToMany(mappedBy="parent", orphanRemoval=true, fetch=FetchType.LAZY, cascade=arrayOf(CascadeType.ALL))
	@OrderColumn(name = "position")
    var children: MutableList<CategoryEntity> = mutableListOf()
        get() = field.toMutableList()
        set(children) {
            field.forEach { c -> c.parent = null }
            field = children.toMutableList()
            field.forEach { c -> c.parent = this }
        }

    private fun removeChild(child: CategoryEntity) {
        children.remove(child)
        child.parent = null
    }

}