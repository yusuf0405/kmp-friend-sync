package org.joseph.friendsync.mappers

import org.joseph.friendsync.common.mapper.Mapper
import org.joseph.friendsync.domain.models.CategoryDomain
import org.joseph.friendsync.models.Category

class CategoryDomainToCategoryMapper : Mapper<CategoryDomain, Category> {

    override fun map(from: CategoryDomain): Category = from.run {
        if (this == CategoryDomain.unknown) return@run Category.unknown
        Category(
            id = id,
            name = name
        )
    }
}