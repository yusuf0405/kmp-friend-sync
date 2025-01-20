package org.joseph.friendsync.data.cloud.mappers

import org.joseph.friendsync.core.Mapper
import org.joseph.friendsync.data.cloud.models.CategoryCloud
import org.joseph.friendsync.data.models.CategoryData

class CategoryCloudToCategoryDataMapper : Mapper<CategoryCloud, CategoryData> {

    override fun map(from: CategoryCloud): CategoryData = from.run {
        CategoryData(id = id, name = name)
    }
}