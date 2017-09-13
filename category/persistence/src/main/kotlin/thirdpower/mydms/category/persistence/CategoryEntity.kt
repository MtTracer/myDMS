package thirdpower.mydms.category.persistence

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.GeneratedValue

@Entity
public data class CategoryEntity(
	@Id
	@GeneratedValue
	var id: Long? = null,	
	var name: String = "") {
	
}