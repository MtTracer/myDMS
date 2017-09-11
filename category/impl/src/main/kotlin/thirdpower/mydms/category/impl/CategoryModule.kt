package thirdpower.mydms.category.impl

import com.google.inject.AbstractModule
import com.google.auto.service.AutoService
import com.google.inject.Module
import thirdpower.mydms.category.api.CategoryService

@AutoService(Module::class)
class CategoryModule : AbstractModule() {
	
	override fun configure() {
		bind(CategoryService::class.java).to(DefaultCategoryService::class);
	}
}