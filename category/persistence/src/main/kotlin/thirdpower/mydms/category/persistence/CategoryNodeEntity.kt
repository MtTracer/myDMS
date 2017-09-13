package thirdpower.mydms.category.persistence

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Column
import javax.persistence.OneToOne
import javax.persistence.EmbeddedId

@Entity
data class CategoryNodeEntity(
		@EmbeddedId
		var id: CategoryNodeId,
		var index: Int) {

	
}

data class CategoryNodeId(
			var categoryId: Long,
			var parentCategoryId: Long
)