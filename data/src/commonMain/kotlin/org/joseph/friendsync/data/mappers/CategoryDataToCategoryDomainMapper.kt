package org.joseph.friendsync.data.mappers

import org.joseph.friendsync.core.Mapper
import org.joseph.friendsync.data.models.CategoryData
import org.joseph.friendsync.domain.models.CategoryDomain
import kotlin.jvm.JvmSuppressWildcards

class CategoryDataToCategoryDomainMapper :
    Mapper<@JvmSuppressWildcards CategoryData, @JvmSuppressWildcards CategoryDomain> {

    override fun map(from: CategoryData): CategoryDomain = from.run {
        CategoryDomain(id = id, name = name)
    }
}