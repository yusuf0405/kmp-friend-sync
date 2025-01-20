package org.joseph.friendsync.data.mappers

import org.joseph.friendsync.core.Mapper
import org.joseph.friendsync.data.models.PostData
import org.joseph.friendsync.domain.models.PostDomain
import kotlin.jvm.JvmSuppressWildcards

internal class PostDataToDomainMapper(
    private val userInfoDataToDomainMapper: UserInfoDataToDomainMapper
) : Mapper<@JvmSuppressWildcards PostData, @JvmSuppressWildcards PostDomain> {

    override fun map(from: PostData): PostDomain = from.run {
        PostDomain(
            commentsCount = commentsCount,
            id = id,
            imageUrls = imageUrls,
            likesCount = likesCount,
            message = message,
            releaseDate = releaseDate,
            savedCount = savedCount,
            user = userInfoDataToDomainMapper.map(user)
        )
    }
}