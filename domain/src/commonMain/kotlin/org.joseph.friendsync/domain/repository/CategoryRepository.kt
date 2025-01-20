package org.joseph.friendsync.domain.repository

import org.joseph.friendsync.domain.models.CategoryDomain

interface CategoryRepository {

    suspend fun addNewCategory(categoryName: String): CategoryDomain

    suspend fun deleteCategoryById(id: Int): String

    suspend fun fetchAllCategories(): List<CategoryDomain>

    suspend fun fetchCategoryById(id: Int): CategoryDomain
}
