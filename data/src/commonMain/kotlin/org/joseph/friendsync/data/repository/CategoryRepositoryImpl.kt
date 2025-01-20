package org.joseph.friendsync.data.repository

import org.joseph.friendsync.data.cloud.source.category.CategoryCloudDataSource
import org.joseph.friendsync.data.mappers.CategoryDataToCategoryDomainMapper
import org.joseph.friendsync.domain.models.CategoryDomain
import org.joseph.friendsync.domain.repository.CategoryRepository

internal class CategoryRepositoryImpl(
    private val categoryCloudDataSource: CategoryCloudDataSource,
    private val categoryDataToCategoryDomainMapper: CategoryDataToCategoryDomainMapper
) : CategoryRepository {

    override suspend fun addNewCategory(categoryName: String): CategoryDomain {
        val categoryData = categoryCloudDataSource.addNewCategory(categoryName)
        return categoryDataToCategoryDomainMapper.map(categoryData)
    }

    override suspend fun deleteCategoryById(id: Int): String {
        return categoryCloudDataSource.deleteCategoryById(id)
    }

    override suspend fun fetchAllCategories(): List<CategoryDomain> {
        val categoriesData = categoryCloudDataSource.fetchAllCategories()
        return categoriesData.map(categoryDataToCategoryDomainMapper::map)
    }

    override suspend fun fetchCategoryById(id: Int): CategoryDomain {
        val categoryData = categoryCloudDataSource.fetchCategoryById(id)
        return categoryDataToCategoryDomainMapper.map(categoryData)
    }
}