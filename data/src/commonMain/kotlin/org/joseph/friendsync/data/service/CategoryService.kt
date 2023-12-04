package org.joseph.friendsync.data.service

import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import io.ktor.http.HttpMethod
import org.joseph.friendsync.common.util.Result
import org.joseph.friendsync.data.models.category.CategoriesResponse
import org.joseph.friendsync.data.models.category.CategoryResponse
import org.joseph.friendsync.data.request

private const val CATEGORIES_REQUEST_PATH = "/categories"

internal class CategoryService(
    private val client: HttpClient
) {

    suspend fun fetchAllCategory(
    ): Result<CategoriesResponse> = client.request("$CATEGORIES_REQUEST_PATH/list") {
        method = HttpMethod.Get
    }

    suspend fun fetchCategoryById(
        categoryId: Int
    ): Result<CategoryResponse> = client.request("$CATEGORIES_REQUEST_PATH/${categoryId}") {
        method = HttpMethod.Get
    }

    suspend fun addNewCategory(
        categoryName: String
    ): Result<CategoryResponse> = client.request("$CATEGORIES_REQUEST_PATH/add") {
        method = HttpMethod.Post
        parameter("category_name", categoryName)
    }

    suspend fun deleteCategoryById(
        categoryId: Int
    ): Result<String> = client.request("$CATEGORIES_REQUEST_PATH/{$categoryId}") {
        method = HttpMethod.Post
    }
}