package org.joseph.friendsync.data.cloud.mappers

import org.joseph.friendsync.core.Mapper
import org.joseph.friendsync.data.models.CategoryData

class CategoryCloudToCategoryDataMapper : Mapper<CategoryData, CategoryData> {

    override fun map(from: CategoryData): CategoryData = from.run {
        CategoryData(id = id, name = name)
    }
}