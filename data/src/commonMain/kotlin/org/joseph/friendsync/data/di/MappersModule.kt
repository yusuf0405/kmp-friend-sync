package org.joseph.friendsync.data.di

import org.joseph.friendsync.core.Mapper
import org.joseph.friendsync.data.cloud.mappers.CategoryCloudToCategoryDataMapper
import org.joseph.friendsync.data.cloud.mappers.CommentCloudToDataMapper
import org.joseph.friendsync.data.cloud.mappers.EditProfileParamsCloudToDataMapper
import org.joseph.friendsync.data.cloud.mappers.LikedPostCloudToDataMapper
import org.joseph.friendsync.data.cloud.mappers.PostCloudToDataMapper
import org.joseph.friendsync.data.cloud.mappers.SubscriptionCloudToDataMapper
import org.joseph.friendsync.data.cloud.mappers.UserDetailCloudToDataMapper
import org.joseph.friendsync.data.cloud.mappers.UserInfoCloudToDataMapper
import org.joseph.friendsync.data.cloud.mappers.UserPersonalInfoCloudToDataMapper
import org.joseph.friendsync.data.cloud.models.CommentCloud
import org.joseph.friendsync.data.cloud.models.PostCloud
import org.joseph.friendsync.data.cloud.models.UserDetailCloud
import org.joseph.friendsync.data.cloud.models.UserPersonalInfoCloud
import org.joseph.friendsync.data.local.mappers.CommentsLocalToDataMapper
import org.joseph.friendsync.data.local.mappers.LikedPostLocalToDataMapper
import org.joseph.friendsync.data.local.mappers.PostLocalToDataMapper
import org.joseph.friendsync.data.local.mappers.SubscriptionLocalToDataMapper
import org.joseph.friendsync.data.local.mappers.UserDetailLocalToDataMapper
import org.joseph.friendsync.data.local.models.CommentEntity
import org.joseph.friendsync.data.local.models.CurrentUserLocal
import org.joseph.friendsync.data.local.models.LikedPostLocal
import org.joseph.friendsync.data.local.models.PostEntity
import org.joseph.friendsync.data.local.models.SubscriptionLocal
import org.joseph.friendsync.data.local.models.UserDetailLocal
import org.joseph.friendsync.data.mappers.AuthResponseDataToAuthResultDataMapper
import org.joseph.friendsync.data.mappers.CategoryDataToCategoryDomainMapper
import org.joseph.friendsync.data.mappers.CommentDataToDomainMapper
import org.joseph.friendsync.data.mappers.CommentDataToLocalMapper
import org.joseph.friendsync.data.mappers.CurrentUserLocalToDomainMapper
import org.joseph.friendsync.data.mappers.EditProfileParamsDataToDomain
import org.joseph.friendsync.data.mappers.EditProfileParamsToCloudMapper
import org.joseph.friendsync.data.mappers.LikedPostDataToDomainMapper
import org.joseph.friendsync.data.mappers.LikedPostDataToLocalMapper
import org.joseph.friendsync.data.mappers.PostDataToDomainMapper
import org.joseph.friendsync.data.mappers.PostDataToLocalMapper
import org.joseph.friendsync.data.mappers.SignUpContextToRequestMapper
import org.joseph.friendsync.data.mappers.SubscriptionDataToDomainMapper
import org.joseph.friendsync.data.mappers.SubscriptionDataToLocalMapper
import org.joseph.friendsync.data.mappers.UserDetailDataToDomainMapper
import org.joseph.friendsync.data.mappers.UserDetailDataToLocalMapper
import org.joseph.friendsync.data.mappers.UserInfoDataToDomainMapper
import org.joseph.friendsync.data.mappers.UserPersonalDataToDomainMapper
import org.joseph.friendsync.data.models.CategoryData
import org.joseph.friendsync.data.models.EditProfileParamsData
import org.joseph.friendsync.data.models.LikedPostData
import org.joseph.friendsync.data.models.PostData
import org.joseph.friendsync.data.models.SubscriptionData
import org.joseph.friendsync.data.models.UserDetailData
import org.joseph.friendsync.data.models.UserPersonalInfoData
import org.joseph.friendsync.data.models.comments.CommentData
import org.joseph.friendsync.data.models.like.LikedPostCloud
import org.joseph.friendsync.data.models.subscription.SubscriptionCloud
import org.joseph.friendsync.data.models.user.EditProfileParamsCloud
import org.joseph.friendsync.data.models.user.SignUpRequest
import org.joseph.friendsync.domain.models.CategoryDomain
import org.joseph.friendsync.domain.models.CommentDomain
import org.joseph.friendsync.domain.models.CurrentUserDomain
import org.joseph.friendsync.domain.models.EditProfileParams
import org.joseph.friendsync.domain.models.LikedPostDomain
import org.joseph.friendsync.domain.models.PostDomain
import org.joseph.friendsync.domain.models.SignUpContext
import org.joseph.friendsync.domain.models.SubscriptionDomain
import org.joseph.friendsync.domain.models.UserDetailDomain
import org.joseph.friendsync.domain.models.UserPersonalInfoDomain
import org.koin.dsl.module
import kotlin.jvm.JvmSuppressWildcards

private val cloudMappersModule = module {
    factory { UserInfoCloudToDataMapper() }
    factory { CommentCloudToDataMapper(get()) }
    factory { PostCloudToDataMapper(get()) }
    factory { AuthResponseDataToAuthResultDataMapper() }
    factory { CategoryCloudToCategoryDataMapper() }
    factory { UserDetailCloudToDataMapper() }
    factory { UserPersonalInfoCloudToDataMapper() }
    factory { EditProfileParamsCloudToDataMapper() }
    factory { LikedPostCloudToDataMapper() }
    factory { SubscriptionCloudToDataMapper() }
}

private val localMappersModule = module {
    factory { CommentsLocalToDataMapper() }
    factory { PostLocalToDataMapper() }
    factory { UserDetailLocalToDataMapper() }
    factory { LikedPostLocalToDataMapper() }
    factory { SubscriptionLocalToDataMapper() }
    factory { CurrentUserLocalToDomainMapper() }
}

private val dataMappersModule = module {
    factory { UserInfoDataToDomainMapper() }
    factory { CommentDataToLocalMapper() }
    factory { PostDataToLocalMapper() }
    factory { SignUpContextToRequestMapper() }
    factory { CategoryDataToCategoryDomainMapper() }
    factory { UserDetailDataToLocalMapper() }
    factory { UserDetailDataToDomainMapper() }
    factory { UserPersonalDataToDomainMapper() }
    factory { EditProfileParamsDataToDomain() }
    factory { EditProfileParamsToCloudMapper() }
    factory { LikedPostDataToLocalMapper() }
    factory { LikedPostDataToDomainMapper() }
    factory { SubscriptionDataToLocalMapper() }
    factory { SubscriptionDataToDomainMapper() }
    factory { PostDataToDomainMapper(get()) }
    factory { CommentDataToDomainMapper(get()) }
}

internal val mappersModule = listOf(cloudMappersModule, localMappersModule, dataMappersModule)