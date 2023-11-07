package org.joseph.friendsync.data.repository

import kotlinx.coroutines.withContext
import org.joseph.friendsync.common.util.Result
import org.joseph.friendsync.common.util.coroutines.DispatcherProvider
import org.joseph.friendsync.common.util.coroutines.callSafe
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
    ): Unit = withContext(dispatcherProvider.io) {
        callSafe(Result.Error(message = defaultErrorMessage)) {
            val category = categoryService.addNewCategory(categoryName)
            Result.Success(
                data = categoryCloudToCategoryDomainMapper.map(category.data!!)
            )
        }
    }

    override suspend fun deleteCategoryById(
        id: Int
    ): Result<String> = withContext(dispatcherProvider.io) {
        callSafe(Result.Error(message = defaultErrorMessage)) {
            categoryService.deleteCategoryById(id)
            Result.Success(data = String())
        }
    }


    override suspend fun fetchAllCategories(
    ): Result<List<CategoryDomain>> = withContext(dispatcherProvider.io) {
        callSafe(Result.Error(message = defaultErrorMessage)) {
            val response = categoryService.fetchAllCategory().data
            if (response != null) {
                Result.Success(
                    data = response.map(categoryCloudToCategoryDomainMapper::map)
                )
            } else {
                Result.Error(message = defaultErrorMessage)
            }
        }
    }

    override suspend fun fetchCategoryById(id: Int): Result<CategoryDomain> =
        withContext(dispatcherProvider.io) {
            callSafe(Result.Error(message = defaultErrorMessage)) {
                val response = categoryService.fetchCategoryById(id).data
                if (response != null) {
                    Result.Success(
                        data = categoryCloudToCategoryDomainMapper.map(response)
                    )
                } else {
                    Result.Error(message = defaultErrorMessage)
                }
            }
        }
}