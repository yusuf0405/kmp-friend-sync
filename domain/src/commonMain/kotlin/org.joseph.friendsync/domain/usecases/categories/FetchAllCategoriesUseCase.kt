package org.joseph.friendsync.domain.usecases.categories

import org.joseph.friendsync.core.Result
import org.joseph.friendsync.domain.models.CategoryDomain
import org.joseph.friendsync.domain.repository.CategoryRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class FetchAllCategoriesUseCase : KoinComponent {

    private val repository by inject<CategoryRepository>()

    suspend operator fun invoke(): Result<List<CategoryDomain>> {
        return Result.Success(repository.fetchAllCategories())
    }
}