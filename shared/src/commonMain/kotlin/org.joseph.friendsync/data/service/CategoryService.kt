package org.joseph.friendsync.data.service

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.joseph.friendsync.common.data.KtorApi
import org.joseph.friendsync.data.models.category.CategoriesResponse
import org.joseph.friendsync.data.models.category.CategoryResponse

private const val CATEGORIES_REQUEST_PATH = "/categories"

internal class CategoryService : KtorApi() {

    suspend fun fetchAllCategory(): CategoriesResponse = client.get {
        endPoint(path = "$CATEGORIES_REQUEST_PATH/list")
    }.body()

    suspend fun fetchCategoryById(categoryId: Int): CategoryResponse = client.get {
        endPoint(path = "/users/${categoryId}")
    }.body()

    suspend fun addNewCategory(categoryName: String): CategoryResponse = client.get {
        endPoint(path = "/add")
        parameter("category_name", categoryName)
    }.body()

    suspend fun deleteCategoryById(categoryId: Int): CategoryResponse = client.get {
        endPoint(path = "/delete/{$categoryId}")
    }.body()
}