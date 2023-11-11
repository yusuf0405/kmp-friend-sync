package org.joseph.friendsync.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.joseph.friendsync.screens.home.onboarding.OnBoardingUiState
import org.joseph.friendsync.common.animation.AnimateFade
import org.joseph.friendsync.common.components.PhotoPostItem
import org.joseph.friendsync.common.components.PrimaryButton
import org.joseph.friendsync.common.components.StoriesList
import org.joseph.friendsync.common.components.TextPostItem
import org.joseph.friendsync.common.extensions.SpacerHeight
import org.joseph.friendsync.common.theme.FriendSyncTheme
import org.joseph.friendsync.common.theme.dimens.ExtraLargeSpacing
import org.joseph.friendsync.common.theme.dimens.LargeSpacing
import org.joseph.friendsync.common.theme.dimens.MediumSpacing
import org.joseph.friendsync.models.Post
import org.joseph.friendsync.models.fakeStories
import org.joseph.friendsync.screens.common.ErrorScreen
import org.joseph.friendsync.screens.common.LoadingScreen
import org.joseph.friendsync.screens.home.onboarding.OnBoardingSelection
import org.joseph.friendsync.strings.MainResStrings

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onBoardingUiState: OnBoardingUiState,
    onEvent: (HomeScreenEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (uiState) {
        is HomeUiState.Initial -> LoadingScreen()

        is HomeUiState.Loading -> LoadingScreen()

        is HomeUiState.Error -> ErrorScreen(
            errorMessage = uiState.message,
            onClick = { onEvent(HomeScreenEvent.RefreshAllData) }
        )

        is HomeUiState.Content -> LoadedHomeScreen(
            uiState = uiState,
            onBoardingUiState = onBoardingUiState,
            onEvent = onEvent,
            modifier = modifier
        )
    }
}

@Composable
fun LoadedHomeScreen(
    uiState: HomeUiState.Content,
    onBoardingUiState: OnBoardingUiState,
    onEvent: (HomeScreenEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(FriendSyncTheme.colors.backgroundPrimary),
        contentPadding = PaddingValues(vertical = ExtraLargeSpacing)
    ) {
        item {
            StoriesList(
                storiesList = fakeStories,
                onStoriesClick = { onEvent(HomeScreenEvent.OnStoriesClick) }
            )
            Spacer(modifier = Modifier.height(ExtraLargeSpacing))
        }

        item {
            AnimateFade(isVisible = onBoardingUiState.shouldShowOnBoarding) {
                OnBoardingSelection(
                    users = onBoardingUiState.users,
                    onUserClick = { user -> onEvent(HomeScreenEvent.OnUserClick(user)) },
                    onBoardingFinish = { onEvent(HomeScreenEvent.OnBoardingFinish) },
                    onFollowButtonClick = { isFollow, user ->
                        onEvent(HomeScreenEvent.OnFollowButtonClick(isFollow, user))
                    },
                )
            }
        }
        itemsIndexed(
            items = uiState.posts,
            key = { _, item -> item.postId }
        ) { index, post ->
            when (post) {
                is Post.PhotoPost -> {
                    PhotoPostItem(
                        post = post,
                        onPostClick = { onEvent(HomeScreenEvent.OnPostClick(it)) },
                        onProfileClick = { onEvent(HomeScreenEvent.OnProfileClick(it)) },
                        onLikeClick = { onEvent(HomeScreenEvent.OnLikeClick) },
                        onCommentClick = { onEvent(HomeScreenEvent.OnCommentClick) }
                    )
                }

                is Post.TextPost -> {
                    TextPostItem(
                        post = post,
                        onPostClick = { onEvent(HomeScreenEvent.OnPostClick(it)) },
                        onProfileClick = { onEvent(HomeScreenEvent.OnProfileClick(it)) },
                        onLikeClick = { onEvent(HomeScreenEvent.OnLikeClick) },
                        onCommentClick = { onEvent(HomeScreenEvent.OnCommentClick) }
                    )
                }
            }
            if (index >= uiState.posts.size - 1
                && !uiState.isPaging
            ) {
                LaunchedEffect(key1 = Unit, block = { onEvent(HomeScreenEvent.FetchMorePosts) })
            }
        }
        if (uiState.isPaging && uiState.posts.isNotEmpty()) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(MediumSpacing)
                        .padding(bottom = LargeSpacing),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}