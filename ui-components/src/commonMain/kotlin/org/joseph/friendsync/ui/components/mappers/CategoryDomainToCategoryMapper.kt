package org.joseph.friendsync.ui.components.mappers

import org.joseph.friendsync.core.Mapper
import org.joseph.friendsync.domain.models.CategoryDomain
import org.joseph.friendsync.ui.components.models.Category

class CategoryDomainToCategoryMapper : Mapper<CategoryDomain, Category> {

    override fun map(from: CategoryDomain): Category = from.run {
        if (this == CategoryDomain.unknown) return@run Category.unknown
        Category(
            id = id,
            name = name
        )
    }
}