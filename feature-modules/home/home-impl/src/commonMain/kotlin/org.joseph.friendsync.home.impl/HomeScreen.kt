package org.joseph.friendsync.home.impl

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import org.joseph.friendsync.core.ui.common.ErrorScreen
import org.joseph.friendsync.core.ui.common.LoadingScreen
import org.joseph.friendsync.core.ui.common.animation.AnimateFade
import org.joseph.friendsync.core.ui.components.PostItem
import org.joseph.friendsync.core.ui.components.StoriesItem
import org.joseph.friendsync.core.ui.strings.MainResStrings
import org.joseph.friendsync.core.ui.theme.FriendSyncTheme
import org.joseph.friendsync.core.ui.theme.dimens.ExtraLargeSpacing
import org.joseph.friendsync.core.ui.theme.dimens.ExtraMediumSpacing
import org.joseph.friendsync.core.ui.theme.dimens.MediumSpacing
import org.joseph.friendsync.home.impl.onboarding.OnBoardingSelection
import org.joseph.friendsync.home.impl.onboarding.OnBoardingUiState
import org.joseph.friendsync.ui.components.models.Stories
import org.joseph.friendsync.ui.components.models.fakeStories

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

        if (uiState.posts.isEmpty()) {
            item {
                Spacer(Modifier.height(ExtraLargeSpacing))
                ErrorScreen(
                    modifier = Modifier.fillMaxSize()
                        .background(FriendSyncTheme.colors.onBackgroundPrimary),
                    errorMessage = MainResStrings.posts_empty_title,
                    onClick = { onEvent(HomeScreenEvent.RefreshAllData) }
                )
            }
        } else itemsIndexed(
            items = uiState.posts,
            key = { _, item -> item.id }
        ) { index, post ->
            PostItem(
                authorImage = post.authorImage,
                authorName = post.authorName,
                imageUrls = post.imageUrls,
                createdAt = post.createdAt,
                commentCount = post.commentCount,
                likesCount = post.likedCount,
                text = post.text,
                onPostClick = { onEvent(HomeScreenEvent.OnPostClick(post.id)) },
                onProfileClick = { onEvent(HomeScreenEvent.OnProfileClick(post.authorId)) },
                onLikeClick = { onEvent(HomeScreenEvent.OnLikeClick) },
                onCommentClick = { onEvent(HomeScreenEvent.OnCommentClick(post.authorId)) }
            )
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
                        .padding(MediumSpacing),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun StoriesList(
    storiesList: List<Stories>,
    onStoriesClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = ExtraLargeSpacing),
        horizontalArrangement = Arrangement.spacedBy(ExtraMediumSpacing)
    ) {
        items(
            items = storiesList.groupBy { it.userId }.toList(),
            key = { stories -> stories.hashCode() }
        ) { stories ->
            StoriesItem(
                avatar = stories.second.first().userImage,
                imageUrl = stories.second.first().imageUrl,
                isViewed = stories.second.all { it.isViewed },
                onStoriesClick = onStoriesClick
            )
        }
    }
}