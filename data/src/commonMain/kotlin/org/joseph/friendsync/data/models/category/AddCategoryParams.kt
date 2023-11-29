package org.joseph.friendsync.data.models.category

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddCategoryParams(
    @SerialName("category_name")
    val categoryName: String,
)