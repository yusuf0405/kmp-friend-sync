package org.joseph.friendsync.home.impl

import org.joseph.friendsync.models.user.UserInfo


sealed class HomeScreenEvent {

    data class OnUserClick(val user: UserInfo) : HomeScreenEvent()

    data class OnFollowButtonClick(
        val isFollow: Boolean,
        val user: UserInfo
    ) : HomeScreenEvent()

    data object FetchMorePosts : HomeScreenEvent()

    data object RefreshAllData : HomeScreenEvent()

    data object OnFollowItemClick : HomeScreenEvent()

    data object OnBoardingFinish : HomeScreenEvent()

    data object OnStoriesClick : HomeScreenEvent()

    data object OnLikeClick : HomeScreenEvent()

    data class OnCommentClick(val postId: Int) : HomeScreenEvent()

    data class OnPostClick(val postId: Int) : HomeScreenEvent()

    data class OnProfileClick(val userId: Int) : HomeScreenEvent()
}