package org.joseph.friendsync.domain.repository

import org.joseph.friendsync.core.Result
import org.joseph.friendsync.domain.models.CategoryDomain

interface CategoryRepository {

    suspend fun addNewCategory(categoryName: String):Result<CategoryDomain>

    suspend fun deleteCategoryById(id: Int): Result<String>

    suspend fun fetchAllCategories(): Result<List<CategoryDomain>>

    suspend fun fetchCategoryById(id: Int): Result<CategoryDomain>
}
