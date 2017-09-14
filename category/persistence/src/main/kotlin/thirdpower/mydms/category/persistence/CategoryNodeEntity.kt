package thirdpower.mydms.category.persistence

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Column
import javax.persistence.OneToOne
import javax.persistence.EmbeddedId
import javax.persistence.OneToMany
import javax.persistence.ManyToOne
import javax.persistence.OrderBy

@Entity
data class CategoryNodeEntity(
    @ManyToOne
    val parent: CategoryEntity,
    val current: CategoryEntity,
    @OneToMany(mappedBy="parent")
	@OrderBy("index")
    val children: List<CategoryEntity>) {
	
}

