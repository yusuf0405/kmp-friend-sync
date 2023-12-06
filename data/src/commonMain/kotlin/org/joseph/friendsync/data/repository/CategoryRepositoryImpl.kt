package org.joseph.friendsync.data.repository

import kotlinx.coroutines.withContext
import org.joseph.friendsync.common.util.Result
import org.joseph.friendsync.common.util.coroutines.DispatcherProvider
import org.joseph.friendsync.common.util.coroutines.callSafe
import org.joseph.friendsync.common.util.filterNotNullOrError
import org.joseph.friendsync.common.util.map
import org.joseph.friendsync.data.mappers.CategoryCloudToCategoryDomainMapper
import org.joseph.friendsync.data.service.CategoryService
import org.joseph.friendsync.domain.models.CategoryDomain
import org.joseph.friendsync.domain.repository.CategoryRepository

internal class CategoryRepositoryImpl(
    private val categoryService: CategoryService,
    private val dispatcherProvider: DispatcherProvider,
    private val categoryCloudToCategoryDomainMapper: CategoryCloudToCategoryDomainMapper,
) : CategoryRepository {

    override suspend fun addNewCategory(
        categoryName: String
    ): Result<CategoryDomain> = withContext(dispatcherProvider.io) {
        callSafe {
            categoryService.addNewCategory(categoryName).map { response ->
                response.data?.let(categoryCloudToCategoryDomainMapper::map)
            }.filterNotNullOrError()
        }
    }

    override suspend fun deleteCategoryById(
        id: Int
    ): Result<String> = withContext(dispatcherProvider.io) {
        callSafe { categoryService.deleteCategoryById(id) }
    }


    override suspend fun fetchAllCategories(
    ): Result<List<CategoryDomain>> = withContext(dispatcherProvider.io) {
        callSafe {
            categoryService.fetchAllCategory().map { response ->
                response.data?.map(categoryCloudToCategoryDomainMapper::map) ?: emptyList()
            }
        }
    }

    override suspend fun fetchCategoryById(
        id: Int
    ): Result<CategoryDomain> = withContext(dispatcherProvider.io) {
        callSafe {
            categoryService.fetchCategoryById(id).map { response ->
                response.data?.let(categoryCloudToCategoryDomainMapper::map)
            }.filterNotNullOrError()
        }
    }
}