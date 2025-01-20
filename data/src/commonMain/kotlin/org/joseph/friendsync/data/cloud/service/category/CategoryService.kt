package org.joseph.friendsync.data.cloud.service.category

import org.joseph.friendsync.data.cloud.models.CategoriesResponse
import org.joseph.friendsync.data.cloud.models.CategoryResponse

internal interface CategoryService {

    suspend fun fetchAllCategory(): CategoriesResponse

    suspend fun fetchCategoryById(categoryId: Int): CategoryResponse

    suspend fun addNewCategory(categoryName: String): CategoryResponse

    suspend fun deleteCategoryById(categoryId: Int): DeletedCategoryId
}