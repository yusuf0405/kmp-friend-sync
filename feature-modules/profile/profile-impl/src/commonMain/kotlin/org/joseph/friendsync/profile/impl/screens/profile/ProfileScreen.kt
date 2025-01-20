package org.joseph.friendsync.profile.impl.screens.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.joseph.friendsync.core.ui.common.EmptyScreen
import org.joseph.friendsync.core.ui.common.ErrorScreen
import org.joseph.friendsync.core.ui.common.LoadingScreen
import org.joseph.friendsync.core.ui.common.toolbar.CollapsibleToolbar
import org.joseph.friendsync.core.ui.components.AppBarIcon
import org.joseph.friendsync.core.ui.components.Placeholder
import org.joseph.friendsync.core.ui.extensions.SpacerHeight
import org.joseph.friendsync.core.ui.extensions.SpacerWidth
import org.joseph.friendsync.core.ui.extensions.clickableNoRipple
import org.joseph.friendsync.core.ui.extensions.collectStateWithLifecycle
import kmp_friend_sync.core_ui.generated.resources.Res
import kmp_friend_sync.core_ui.generated.resources.edit_profile
import kmp_friend_sync.core_ui.generated.resources.follow_button_text
import kmp_friend_sync.core_ui.generated.resources.followers
import kmp_friend_sync.core_ui.generated.resources.following
import kmp_friend_sync.core_ui.generated.resources.unsubscribe
import org.jetbrains.compose.resources.stringResource
import org.joseph.friendsync.core.ui.theme.FriendSyncTheme
import org.joseph.friendsync.core.ui.theme.dimens.ExtraLargeSpacing
import org.joseph.friendsync.core.ui.theme.dimens.ExtraMediumSpacing
import org.joseph.friendsync.core.ui.theme.dimens.ExtraSmallSpacing
import org.joseph.friendsync.core.ui.theme.dimens.LargeSpacing
import org.joseph.friendsync.core.ui.theme.dimens.MediumSpacing
import org.joseph.friendsync.ui.components.models.Post
import org.joseph.friendsync.ui.components.models.user.UserDetail

@Composable
internal fun ProfileScreen(
    viewModel: ProfileViewModel,
    onNavigateUp: () -> Unit,
) {
    val modifier = Modifier
        .fillMaxSize()
        .background(FriendSyncTheme.colors.backgroundPrimary)

    val uiState: ProfileUiState by viewModel.state.collectStateWithLifecycle()
    val postsUiState by viewModel.postsUiStateFlow.collectStateWithLifecycle()
    val shouldCurrentUser by viewModel.shouldCurrentUserFlow.collectStateWithLifecycle()
    val hasUserSubscription by viewModel.hasUserSubscriptionFlow.collectStateWithLifecycle()

    when (uiState) {
        is ProfileUiState.Initial -> Unit
        is ProfileUiState.Loading -> LoadingScreen(modifier = modifier)
        is ProfileUiState.Error -> ErrorScreen(
            modifier = modifier,
            errorMessage = (uiState as ProfileUiState.Error).errorMessage,
            onClick = {}
        )

        is ProfileUiState.Content -> {
            LoadedProfileScreen(
                uiState = uiState as ProfileUiState.Content,
                postsUiState = postsUiState,
                shouldCurrentUser = shouldCurrentUser,
                hasUserSubscription = hasUserSubscription,
                modifier = modifier,
                onEvent = viewModel::onEvent,
                onNavigateUp = onNavigateUp
            )
        }
    }
}

@Composable
private fun LoadedProfileScreen(
    uiState: ProfileUiState.Content,
    postsUiState: ProfilePostsUiState,
    shouldCurrentUser: Boolean,
    hasUserSubscription: Boolean,
    onEvent: (ProfileScreenEvent) -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    val lazyListState = rememberLazyStaggeredGridState()
    val statusBarHeight = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

    Box(modifier = modifier) {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            state = lazyListState
        ) {
            item(span = StaggeredGridItemSpan.FullLine) {
                UserImageWithInfo(
                    user = uiState.user,
                    statusBarHeight = statusBarHeight,
                    hasUserSubscription = hasUserSubscription,
                    shouldCurrentUser = shouldCurrentUser,
                    onEvent = onEvent,
                    onNavigateUp = onNavigateUp,
                )
            }

            when (postsUiState) {
                is ProfilePostsUiState.Loading -> {
                    item(span = StaggeredGridItemSpan.FullLine) { LoadingScreen() }
                }

                is ProfilePostsUiState.Error -> {}
                is ProfilePostsUiState.Content -> {
                    if (postsUiState.posts.isEmpty()) {
                        item(span = StaggeredGridItemSpan.FullLine) { EmptyScreen() }
                    } else items(
                        items = postsUiState.posts,
                        key = { item -> item.id }
                    ) { post ->
                        PostItem(
                            post = post,
                            onClick = { onEvent(ProfileScreenEvent.OnPostClick(post.id)) }
                        )
                    }
                }
            }
        }

        CollapsibleToolbar(
            modifier = Modifier.align(Alignment.TopCenter),
            title = uiState.user.fullName(),
            statusBarHeight = statusBarHeight,
            lazyScrollState = lazyListState,
            onNavigateUp = onNavigateUp
        )
    }
}

@Composable
fun PostItem(
    post: Post,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(MediumSpacing),
        elevation = CardDefaults.cardElevation(
            defaultElevation = FriendSyncTheme.dimens.dp4
        ),
        shape = FriendSyncTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = FriendSyncTheme.colors.backgroundModal
        )
    ) {
        Box(
            modifier = Modifier.clickableNoRipple(onClick),
            contentAlignment = Alignment.Center
        ) {
            if (post.imageUrls.isNotEmpty()) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    model = post.imageUrls.first(),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                )
            } else if (post.text.isNotEmpty()) {
                Text(
                    modifier = Modifier.padding(ExtraLargeSpacing),
                    text = post.text,
                    style = FriendSyncTheme.typography.bodyExtraSmall.semiBold,
                    textAlign = TextAlign.Center,
                    lineHeight = FriendSyncTheme.dimens.sp20,
                    maxLines = 6,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
private fun UserImageWithInfo(
    user: UserDetail,
    hasUserSubscription: Boolean,
    shouldCurrentUser: Boolean,
    statusBarHeight: Dp,
    onEvent: (ProfileScreenEvent) -> Unit,
    onNavigateUp: () -> Unit,
) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .background(Placeholder())
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = user.avatar,
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )

            AppBarIcon(
                modifier = Modifier
                    .padding(
                        start = ExtraMediumSpacing,
                        top = statusBarHeight + LargeSpacing
                    )
                    .align(Alignment.TopStart),
                imageVector = Icons.Filled.ArrowBack,
                onClick = onNavigateUp,
                background = FriendSyncTheme.colors.backgroundPrimary
            )

            val colors = arrayOf(
                Pair(0.1f, Color.Transparent),
                Pair(1f, FriendSyncTheme.colors.backgroundPrimary),
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .height(FriendSyncTheme.dimens.dp800)
                    .background(Brush.verticalGradient(colorStops = colors))
            )
        }
        Column(
            modifier = Modifier
                .offset(y = (-48).dp)
                .padding(horizontal = MediumSpacing)
                .fillMaxWidth()
        ) {
            Text(
                text = user.fullName(),
                style = FriendSyncTheme.typography.titleLarge.bold,
                fontSize = FriendSyncTheme.dimens.sp38
            )
            SpacerHeight(LargeSpacing)
            UserInfo(
                user = user,
                hasUserSubscription = hasUserSubscription,
                shouldCurrentUser = shouldCurrentUser,
                onFollowClick = {
                    onEvent(ProfileScreenEvent.OnFollowButtonClick(it, user.id))
                },
                onEditClick = {
                    onEvent(ProfileScreenEvent.OnEditProfile)
                },
            )
        }
    }

}

@Composable
private fun UserInfo(
    user: UserDetail,
    hasUserSubscription: Boolean,
    shouldCurrentUser: Boolean,
    onFollowClick: (Boolean) -> Unit,
    onEditClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(ExtraSmallSpacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FollowButton(
            modifier = Modifier
                .width(FriendSyncTheme.dimens.dp130)
                .height(FriendSyncTheme.dimens.dp38),
            isSubscribed = hasUserSubscription,
            shouldCurrentUser = shouldCurrentUser,
            onClick = onFollowClick,
            onEditClick = onEditClick
        )
        OutlinedCard(
            modifier = Modifier.size(FriendSyncTheme.dimens.dp38),
            border = BorderStroke(
                FriendSyncTheme.dimens.dp2,
                FriendSyncTheme.colors.backgroundModal
            ),
            colors = CardDefaults.cardColors(
                containerColor = FriendSyncTheme.colors.backgroundPrimary
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = FriendSyncTheme.dimens.dp4
            ),
        ) {
            Box(
                modifier = Modifier.fillMaxSize().clickable { },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(FriendSyncTheme.dimens.dp14),
                    imageVector = Icons.Filled.Message,
                    contentDescription = null,
                    tint = FriendSyncTheme.colors.iconsSecondary
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Column {
            Text(
                text = user.followersCount.toString(),
                style = FriendSyncTheme.typography.bodyExtraMedium.semiBold,
            )
            Text(
                text = stringResource(Res.string.followers),
                style = FriendSyncTheme.typography.bodyExtraSmall.regular,
                color = FriendSyncTheme.colors.textSecondary
            )
        }
        SpacerWidth(ExtraMediumSpacing)
        Column {
            Text(
                text = user.followingCount.toString(),
                style = FriendSyncTheme.typography.bodyExtraMedium.semiBold,
            )
            Text(
                text = stringResource(Res.string.following),
                style = FriendSyncTheme.typography.bodyExtraSmall.regular,
                color = FriendSyncTheme.colors.textSecondary
            )
        }
        SpacerWidth(ExtraMediumSpacing)
        OutlinedCard(
            modifier = Modifier.size(FriendSyncTheme.dimens.dp38),
            elevation = CardDefaults.cardElevation(
                defaultElevation = FriendSyncTheme.dimens.dp4
            ),
            colors = CardDefaults.cardColors(
                containerColor = FriendSyncTheme.colors.backgroundModal
            ),
            border = BorderStroke(
                FriendSyncTheme.dimens.dp0,
                Color.Transparent
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier,
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = null,
                    tint = FriendSyncTheme.colors.iconsSecondary
                )
            }
        }
    }
}

@Composable
private fun FollowButton(
    isSubscribed: Boolean,
    shouldCurrentUser: Boolean,
    onClick: (Boolean) -> Unit,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val text = if (shouldCurrentUser) Res.string.edit_profile
    else if (isSubscribed) Res.string.unsubscribe
    else Res.string.follow_button_text

    OutlinedCard(
        modifier = modifier
            .width(FriendSyncTheme.dimens.dp130)
            .height(FriendSyncTheme.dimens.dp38),
        shape = CardDefaults.outlinedShape,
        border = BorderStroke(
            FriendSyncTheme.dimens.dp2,
            followButtonBorderColor(isSubscribed, shouldCurrentUser)
        ),
        colors = CardDefaults.cardColors(
            containerColor = FriendSyncTheme.colors.backgroundPrimary
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = FriendSyncTheme.dimens.dp4
        ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    if (shouldCurrentUser) onEditClick()
                    else onClick(isSubscribed)
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier,
                text = stringResource(text),
                style = FriendSyncTheme.typography.bodyMedium.medium,
                color = followButtonTextColor(isSubscribed, shouldCurrentUser),
                maxLines = 1,
            )
        }
    }
}

@Composable
private fun followButtonBorderColor(
    isSubscribed: Boolean,
    shouldCurrentUser: Boolean
): Color {
    return if (isSubscribed || shouldCurrentUser) FriendSyncTheme.colors.backgroundModal
    else FriendSyncTheme.colors.primary.copy(alpha = 0.2f)
}

@Composable
private fun followButtonTextColor(
    isSubscribed: Boolean,
    shouldCurrentUser: Boolean
): Color {
    return if (isSubscribed || shouldCurrentUser) FriendSyncTheme.colors.textSecondary
    else FriendSyncTheme.colors.primary
}