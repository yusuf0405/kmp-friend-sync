package org.joseph.friendsync.data.service

import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import io.ktor.http.HttpMethod
import org.joseph.friendsync.common.util.Result
import org.joseph.friendsync.data.models.category.CategoriesResponse
import org.joseph.friendsync.data.models.category.CategoryResponse
import org.joseph.friendsync.data.request
import org.joseph.friendsync.data.utils.ADD_REQUEST_PATH
import org.joseph.friendsync.data.utils.CATEGORIES_REQUEST_PATH
import org.joseph.friendsync.data.utils.CATEGORY_NAME_PARAM
import org.joseph.friendsync.data.utils.LIST_REQUEST_PATH

internal class CategoryService(
    private val client: HttpClient
) {

    suspend fun fetchAllCategory(
    ): Result<CategoriesResponse> = client.request("$CATEGORIES_REQUEST_PATH$LIST_REQUEST_PATH") {
        method = HttpMethod.Get
    }

    suspend fun fetchCategoryById(
        categoryId: Int
    ): Result<CategoryResponse> = client.request("$CATEGORIES_REQUEST_PATH/${categoryId}") {
        method = HttpMethod.Get
    }

    suspend fun addNewCategory(
        categoryName: String
    ): Result<CategoryResponse> = client.request("$CATEGORIES_REQUEST_PATH$ADD_REQUEST_PATH") {
        method = HttpMethod.Post
        parameter(CATEGORY_NAME_PARAM, categoryName)
    }

    suspend fun deleteCategoryById(
        categoryId: Int
    ): Result<String> = client.request("$CATEGORIES_REQUEST_PATH/{$categoryId}") {
        method = HttpMethod.Post
    }
}