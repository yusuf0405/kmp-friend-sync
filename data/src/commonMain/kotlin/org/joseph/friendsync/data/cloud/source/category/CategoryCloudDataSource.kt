package org.joseph.friendsync.data.cloud.source.category

import org.joseph.friendsync.data.cloud.service.category.DeletedCategoryId
import org.joseph.friendsync.data.models.CategoryData

internal interface CategoryCloudDataSource {

    suspend fun addNewCategory(categoryName: String): CategoryData

    suspend fun deleteCategoryById(id: Int): DeletedCategoryId

    suspend fun fetchAllCategories(): List<CategoryData>

    suspend fun fetchCategoryById(id: Int): CategoryData
}