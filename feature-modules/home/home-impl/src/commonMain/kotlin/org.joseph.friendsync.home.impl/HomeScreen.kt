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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.joseph.friendsync.core.ui.common.ErrorScreen
import org.joseph.friendsync.core.ui.common.LoadingScreen
import org.joseph.friendsync.core.ui.common.animation.AnimateFade
import org.joseph.friendsync.core.ui.components.PostItem
import org.joseph.friendsync.core.ui.components.StoriesItem
import kmp_friend_sync.core_ui.generated.resources.Res
import kmp_friend_sync.core_ui.generated.resources.posts_empty_title
import org.jetbrains.compose.resources.stringResource
import org.joseph.friendsync.core.ui.theme.FriendSyncTheme
import org.joseph.friendsync.core.ui.theme.dimens.ExtraLargeSpacing
import org.joseph.friendsync.core.ui.theme.dimens.ExtraMediumSpacing
import org.joseph.friendsync.core.ui.theme.dimens.MediumSpacing
import org.joseph.friendsync.home.impl.onboarding.OnBoardingSelection
import org.joseph.friendsync.home.impl.onboarding.OnBoardingUiState
import org.joseph.friendsync.ui.components.models.Stories
import org.joseph.friendsync.ui.components.models.fakeStories

@Composable
internal fun HomeScreen(
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier,
) {
    val onBoardingUiState: OnBoardingUiState by viewModel.onBoardingUiState.collectAsStateWithLifecycle()
    val uiState: HomeUiState by viewModel.state.collectAsStateWithLifecycle()

    when (uiState) {
        is HomeUiState.Initial -> LoadingScreen()

        is HomeUiState.Loading -> LoadingScreen()

        is HomeUiState.Error -> ErrorScreen(
            errorMessage = (uiState as HomeUiState.Error).message,
            onClick = { viewModel.onEvent(HomeScreenEvent.RefreshAllData) }
        )

        is HomeUiState.Content -> LoadedHomeScreen(
            uiState = uiState as HomeUiState.Content,
            onBoardingUiState = onBoardingUiState,
            onEvent = viewModel::onEvent,
            modifier = modifier
        )
    }
}

@Composable
internal fun LoadedHomeScreen(
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
                    onUserClick = { user -> onEvent(HomeScreenEvent.OnUserClick(user.userInfo)) },
                    onBoardingFinish = { onEvent(HomeScreenEvent.OnBoardingFinish) },
                    onFollowButtonClick = { isFollow, user ->
                        onEvent(HomeScreenEvent.OnFollowButtonClick(isFollow, user.userInfo))
                    }
                )
            }
        }

        println("uiState.postMarks.isEmpty() -> ${uiState.postMarks.isEmpty()}")
        if (uiState.postMarks.isEmpty()) {
            item {
                Spacer(Modifier.height(ExtraLargeSpacing))
                ErrorScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(FriendSyncTheme.colors.onBackgroundPrimary),
                    errorMessage = stringResource(Res.string.posts_empty_title),
                    onClick = { onEvent(HomeScreenEvent.RefreshAllData) }
                )
            }
        } else itemsIndexed(
            items = uiState.postMarks,
            key = { _, item -> item.post.id }
        ) { index, postMark ->
            val post = postMark.post
            PostItem(
                post = post,
                isLiked = postMark.isLiked,
                onPostClick = { onEvent(HomeScreenEvent.OnPostClick(post.id)) },
                onProfileClick = { onEvent(HomeScreenEvent.OnProfileClick(post.authorId)) },
                onLikeClick = { onEvent(HomeScreenEvent.OnLikeClick(post.id, postMark.isLiked)) },
                onCommentClick = { onEvent(HomeScreenEvent.OnCommentClick(post.authorId)) }
            )
            if (index >= uiState.postMarks.size - 1
                && !uiState.isPaging
            ) {
                LaunchedEffect(key1 = Unit, block = { onEvent(HomeScreenEvent.FetchMorePosts) })
            }
        }
        if (uiState.isPaging && uiState.postMarks.isNotEmpty()) {
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
internal fun StoriesList(
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