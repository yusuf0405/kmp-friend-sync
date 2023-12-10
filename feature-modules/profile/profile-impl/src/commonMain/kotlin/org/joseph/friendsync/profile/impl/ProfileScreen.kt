package org.joseph.friendsync.profile.impl

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.rememberImagePainter
import compose.icons.FeatherIcons
import compose.icons.feathericons.ArrowLeft
import compose.icons.feathericons.Edit
import kotlinx.coroutines.launch
import org.joseph.friendsync.core.ui.common.ErrorScreen
import org.joseph.friendsync.core.ui.common.LoadingScreen
import org.joseph.friendsync.core.ui.components.AppBarIcon
import org.joseph.friendsync.core.ui.components.CircularImage
import org.joseph.friendsync.core.ui.components.FollowButton
import org.joseph.friendsync.core.ui.components.Placeholder
import org.joseph.friendsync.core.ui.strings.MainResStrings
import org.joseph.friendsync.core.ui.theme.FriendSyncTheme
import org.joseph.friendsync.core.ui.theme.dimens.ExtraLargeSpacing
import org.joseph.friendsync.core.ui.theme.dimens.LargeSpacing
import org.joseph.friendsync.core.ui.theme.dimens.MediumSpacing
import org.joseph.friendsync.core.ui.theme.dimens.SmallSpacing
import org.joseph.friendsync.models.user.UserDetail

@Composable
fun ProfileScreen(
    uiState: ProfileUiState,
    shouldCurrentUser: Boolean,
    onEvent: (ProfileScreenEvent) -> Unit
) {
    val modifier = Modifier
        .fillMaxSize()
        .background(FriendSyncTheme.colors.backgroundPrimary)

    when (uiState) {
        is ProfileUiState.Initial -> Unit
        is ProfileUiState.Loading -> LoadingScreen(modifier = modifier)
        is ProfileUiState.Error -> ErrorScreen(
            modifier = modifier,
            errorMessage = uiState.errorMessage,
            onClick = {}
        )

        is ProfileUiState.Content -> {
            LoadedProfileScreen(
                uiState = uiState,
                shouldCurrentUser = shouldCurrentUser,
                modifier = modifier,
                onEvent = onEvent
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LoadedProfileScreen(
    uiState: ProfileUiState.Content,
    shouldCurrentUser: Boolean,
    onEvent: (ProfileScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val profileTabs = uiState.tabs
    val pagerState = rememberPagerState { profileTabs.size }
    val coroutineScope = rememberCoroutineScope()

    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        val screenHeight = maxHeight
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = scrollState)
        ) {
            UserPersonalInfo(
                userDetail = uiState.userDetail,
                shouldCurrentUser = shouldCurrentUser,
                onEvent = onEvent
            )
            Column(modifier = Modifier.height(screenHeight)) {
                TabRow(
                    modifier = Modifier.fillMaxWidth(),
                    containerColor = FriendSyncTheme.colors.backgroundPrimary,
                    contentColor = FriendSyncTheme.colors.backgroundPrimary,
                    selectedTabIndex = pagerState.currentPage,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            modifier = Modifier
                                .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                                .clip(RoundedCornerShape(4.dp)),
                            height = 3.dp,
                            color = FriendSyncTheme.colors.primary
                        )
                    }
                ) {
                    profileTabs.forEachIndexed { index, tab ->
                        Tab(
                            text = {
                                Text(
                                    modifier = Modifier.padding(vertical = FriendSyncTheme.dimens.dp4),
                                    text = tab.title,
                                    style = FriendSyncTheme.typography.bodyExtraMedium.medium,
                                    color = FriendSyncTheme.colors.textPrimary
                                )
                            },
                            selected = pagerState.currentPage == index,
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            }
                        )
                    }
                }

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxHeight()
                        .nestedScroll(remember {
                            object : NestedScrollConnection {
                                override fun onPreScroll(
                                    available: Offset,
                                    source: NestedScrollSource
                                ): Offset {
                                    return if (available.y > 0) Offset.Zero else Offset(
                                        x = 0f,
                                        y = -scrollState.dispatchRawDelta(-available.y)
                                    )
                                }
                            }
                        })
                )
                { page: Int ->
                    profileTabs[page].content()
                }
            }
        }
    }
}

@Composable
private fun UserPersonalInfo(
    userDetail: UserDetail,
    shouldCurrentUser: Boolean,
    onEvent: (ProfileScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val painter = rememberImagePainter(userDetail.profileBackground ?: String())

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .height(FriendSyncTheme.dimens.dp160)
        ) {
            Image(
                painter = painter,
                modifier = Modifier.background(Placeholder()),
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
            if (shouldCurrentUser) ProfileIcon(
                icon = FeatherIcons.Edit,
                alignment = Alignment.TopEnd,
                onClick = { onEvent(ProfileScreenEvent.OnEditBackgroundImage) }
            )
            else ProfileIcon(
                icon = FeatherIcons.ArrowLeft,
                alignment = Alignment.TopStart,
                onClick = { onEvent(ProfileScreenEvent.OnNavigateToBack) }
            )
        }
        Column(
            modifier = Modifier
                .padding(top = FriendSyncTheme.dimens.dp80)
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CircularImage(
                imageUrl = userDetail.avatar,
                modifier = Modifier.size(150.dp),
            )
            Spacer(modifier = Modifier.height(LargeSpacing))
            Text(
                text = userDetail.fullName(),
                style = FriendSyncTheme.typography.bodyLarge.medium,
            )
            Spacer(modifier = Modifier.height(SmallSpacing))
            if (userDetail.education != null) Text(
                modifier = Modifier.padding(horizontal = ExtraLargeSpacing),
                text = userDetail.education!!,
                style = FriendSyncTheme.typography.bodyMedium.medium,
                color = FriendSyncTheme.colors.textSecondary
            )
            Spacer(modifier = Modifier.height(MediumSpacing))
            if (userDetail.bio != null) {
                Text(
                    modifier = Modifier.padding(horizontal = ExtraLargeSpacing),
                    text = userDetail.bio!!,
                    style = FriendSyncTheme.typography.bodyLarge.medium,
                )
                Spacer(modifier = Modifier.height(ExtraLargeSpacing))
            }
            if (userDetail != UserDetail.unknown) FollowingInfo(
                userDetail = userDetail,
                shouldCurrentUser = shouldCurrentUser,
                onEvent = onEvent
            )
        }
    }
}

@Composable
private fun BoxScope.ProfileIcon(
    icon: ImageVector,
    alignment: Alignment,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(LargeSpacing)
            .align(alignment)
            .size(FriendSyncTheme.dimens.dp36)
            .clip(CircleShape)
            .background(FriendSyncTheme.colors.backgroundModal),
        contentAlignment = Alignment.Center
    ) {
        IconButton(onClick = onClick) {
            Icon(
                modifier = Modifier.size(FriendSyncTheme.dimens.dp16),
                imageVector = icon,
                contentDescription = null,
                tint = FriendSyncTheme.colors.iconsPrimary
            )
        }
    }
}

@Composable
private fun FollowingInfo(
    userDetail: UserDetail,
    shouldCurrentUser: Boolean,
    onEvent: (ProfileScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(horizontal = ExtraLargeSpacing)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = userDetail.followersCount.toString(),
                style = FriendSyncTheme.typography.bodyLarge.medium,
            )
            Text(
                text = MainResStrings.followers,
                style = FriendSyncTheme.typography.bodyMedium.medium,
                color = FriendSyncTheme.colors.textSecondary
            )
        }
        Column {
            Text(
                text = userDetail.followingCount.toString(),
                style = FriendSyncTheme.typography.bodyLarge.medium,
            )
            Text(
                text = MainResStrings.following,
                style = FriendSyncTheme.typography.bodyMedium.medium,
                color = FriendSyncTheme.colors.textSecondary
            )
        }

        if (shouldCurrentUser) OutlinedButton(
            modifier = Modifier,
            onClick = { onEvent(ProfileScreenEvent.OnEditProfile) },
            border = BorderStroke(1.dp, FriendSyncTheme.colors.iconsSecondary),
        ) {
            Text(
                text = MainResStrings.edit_profile,
                style = FriendSyncTheme.typography.bodyMedium.medium,
                color = FriendSyncTheme.colors.textPrimary
            )
        } else FollowButton(
            onClick = {},
            modifier = Modifier,
        )
    }
}
