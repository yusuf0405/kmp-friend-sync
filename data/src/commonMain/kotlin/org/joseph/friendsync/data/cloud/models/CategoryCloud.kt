package org.joseph.friendsync.data.cloud.models

import kotlinx.serialization.Serializable

@Serializable
data class CategoryCloud(
    val id: Int,
    val name: String
)

@Serializable
data class CategoryResponse(
    val data: CategoryCloud? = null,
    val errorMessage: String? = null
)

@Serializable
data class CategoriesResponse(
    val data: List<CategoryCloud>? = null,
    val errorMessage: String? = null
)