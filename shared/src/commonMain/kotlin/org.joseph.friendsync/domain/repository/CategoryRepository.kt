package org.joseph.friendsync.domain.repository

import org.joseph.friendsync.common.util.Result
import org.joseph.friendsync.domain.models.CategoryDomain

internal interface CategoryRepository {

    suspend fun addNewCategory(categoryName: String)

    suspend fun deleteCategoryById(id: Int): Result<String>

    suspend fun fetchAllCategories(): Result<List<CategoryDomain>>

    suspend fun fetchCategoryById(id: Int): Result<CategoryDomain>
}
