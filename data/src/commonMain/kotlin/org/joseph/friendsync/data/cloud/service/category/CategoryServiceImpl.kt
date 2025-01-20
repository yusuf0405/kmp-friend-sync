package org.joseph.friendsync.data.cloud.service.category

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import org.joseph.friendsync.data.cloud.ADD_REQUEST_PATH
import org.joseph.friendsync.data.cloud.CATEGORIES_REQUEST_PATH
import org.joseph.friendsync.data.cloud.CATEGORY_NAME_PARAM
import org.joseph.friendsync.data.cloud.LIST_REQUEST_PATH
import org.joseph.friendsync.data.cloud.models.CategoriesResponse
import org.joseph.friendsync.data.cloud.models.CategoryResponse

internal typealias DeletedCategoryId = String

internal class CategoryServiceImpl(private val client: HttpClient) : CategoryService {

    override suspend fun fetchAllCategory(): CategoriesResponse {
        return client.get("$CATEGORIES_REQUEST_PATH$LIST_REQUEST_PATH").body()
    }

    override suspend fun fetchCategoryById(categoryId: Int): CategoryResponse {
        return client.get("$CATEGORIES_REQUEST_PATH/${categoryId}").body()
    }

    override suspend fun addNewCategory(
        categoryName: String
    ): CategoryResponse {
        return client.post("$CATEGORIES_REQUEST_PATH$ADD_REQUEST_PATH") {
            parameter(CATEGORY_NAME_PARAM, categoryName)
        }.body()
    }

    override suspend fun deleteCategoryById(categoryId: Int): DeletedCategoryId {
        return client.post("$CATEGORIES_REQUEST_PATH/{$categoryId}").body()
    }
}