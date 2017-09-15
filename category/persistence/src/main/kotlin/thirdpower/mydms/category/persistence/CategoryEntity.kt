package thirdpower.mydms.category.persistence

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.OrderColumn
import javax.persistence.Table
import javax.persistence.GenerationType
import javax.persistence.Column
import javax.persistence.JoinColumn
import java.util.ArrayList
import com.google.common.base.MoreObjects
import javax.persistence.FetchType
import javax.persistence.CascadeType

@Entity
@Table(name="CATEGORY")
public data class CategoryEntity(
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	var id: Long? = null,	

	@Column(name="NAME")
	var name: String = "",
	
	@ManyToOne(optional = true, fetch=FetchType.LAZY, cascade=arrayOf(CascadeType.ALL))
	@JoinColumn(name="PARENT_ID")
	var parent: CategoryEntity? =null

) {
	
	@OneToMany(mappedBy="parent", orphanRemoval=true, fetch=FetchType.LAZY, cascade=arrayOf(CascadeType.ALL))
	@OrderColumn(name = "position")
	var children: List<CategoryEntity> = ArrayList();
	
}