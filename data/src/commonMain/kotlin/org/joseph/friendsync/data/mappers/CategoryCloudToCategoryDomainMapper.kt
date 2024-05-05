package org.joseph.friendsync.data.mappers

import org.joseph.friendsync.core.Mapper
import org.joseph.friendsync.data.models.category.CategoryCloud
import org.joseph.friendsync.domain.models.CategoryDomain

class CategoryCloudToCategoryDomainMapper : Mapper<CategoryCloud, CategoryDomain> {

    override fun map(from: CategoryCloud): CategoryDomain = from.run {
        CategoryDomain(
            id = id,
            name = name
        )
    }
}