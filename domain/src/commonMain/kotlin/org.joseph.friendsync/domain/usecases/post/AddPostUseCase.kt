package org.joseph.friendsync.domain.usecases.post

import org.joseph.friendsync.common.util.Result
import org.joseph.friendsync.domain.models.PostDomain
import org.joseph.friendsync.domain.repository.PostRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class AddPostUseCase : KoinComponent {

    private val repository by inject<PostRepository>()

    suspend operator fun invoke(
        byteArray: List<ByteArray?>,
        message: String?,
        userId: Int,
    ): Result<PostDomain> {
        return repository.addPost(
            byteArray = byteArray,
            message = message,
            userId = userId
        )
    }
}