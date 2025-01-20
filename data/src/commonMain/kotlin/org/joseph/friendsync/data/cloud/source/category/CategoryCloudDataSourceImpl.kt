package org.joseph.friendsync.data.cloud.source.category

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.withContext
import org.joseph.friendsync.core.DispatcherProvider
import org.joseph.friendsync.core.Mapper
import org.joseph.friendsync.data.cloud.models.CategoryCloud
import org.joseph.friendsync.data.cloud.service.category.CategoryService
import org.joseph.friendsync.data.cloud.service.category.DeletedCategoryId
import org.joseph.friendsync.data.models.CategoryData

internal class CategoryCloudDataSourceImpl(
    private val categoryService: CategoryService,
    private val dispatcherProvider: DispatcherProvider,
    private val categoryCloudToCategoryDataMapper: Mapper<CategoryCloud, CategoryData>,
) : CategoryCloudDataSource {

    override suspend fun addNewCategory(categoryName: String): CategoryData {
        return withContext(dispatcherProvider.io) {
            try {
                val categoryCloud = categoryService.addNewCategory(categoryName).data
                categoryCloudToCategoryDataMapper.map(checkNotNull(categoryCloud))
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IllegalStateException("Failed to add new category from cloud", e)
            }
        }
    }

    override suspend fun deleteCategoryById(id: Int): DeletedCategoryId {
        return withContext(dispatcherProvider.io) {
            try {
                categoryService.deleteCategoryById(id)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IllegalStateException("Failed to delete category from cloud", e)
            }
        }
    }

    override suspend fun fetchAllCategories(): List<CategoryData> {
        return withContext(dispatcherProvider.io) {
            try {
                val categories = categoryService.fetchAllCategory().data ?: emptyList()
                categories.map(categoryCloudToCategoryDataMapper::map)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IllegalStateException("Failed to fetch all categories from cloud", e)
            }
        }
    }

    override suspend fun fetchCategoryById(id: Int): CategoryData {
        return withContext(dispatcherProvider.io) {
            try {
                val categoryCloud = categoryService.fetchCategoryById(id).data
                categoryCloudToCategoryDataMapper.map(checkNotNull(categoryCloud))
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IllegalStateException("Failed to fetch category from cloud", e)
            }
        }
    }
}