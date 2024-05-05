package org.joseph.friendsync.profile.impl.screens.current.user

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import kotlinx.coroutines.launch
import org.joseph.friendsync.core.ui.common.EmptyScreen
import org.joseph.friendsync.core.ui.common.ErrorScreen
import org.joseph.friendsync.core.ui.common.LoadingScreen
import org.joseph.friendsync.core.ui.common.animation.AnimateSlideTop
import org.joseph.friendsync.core.ui.common.toolbar.CollapsibleToolbar
import org.joseph.friendsync.core.ui.components.AppTopBar
import org.joseph.friendsync.core.ui.strings.MainResStrings
import org.joseph.friendsync.core.ui.theme.FriendSyncTheme
import org.joseph.friendsync.core.ui.theme.dimens.SmallSpacing
import org.joseph.friendsync.profile.impl.models.ProfileTab
import org.joseph.friendsync.profile.impl.screens.current.user.tabs.posts.CurrentUserPostsUiState
import org.joseph.friendsync.profile.impl.screens.current.user.tabs.posts.CurrentUserPostsScreen
import org.joseph.friendsync.core.ui.extensions.collectStateWithLifecycle

@Composable
internal fun CurrentUserScreen(
    viewModel: CurrentUserViewModel,
    hasUserSubscription: Boolean,
) {
    val modifier = Modifier
        .fillMaxSize()
        .background(FriendSyncTheme.colors.backgroundPrimary)

    val uiState by viewModel.state.collectStateWithLifecycle()
    val postsUiState by viewModel.postsUiStateFlow.collectStateWithLifecycle()

    when (uiState) {
        is CurrentUserUiState.Initial -> Unit
        is CurrentUserUiState.Loading -> LoadingScreen(modifier = modifier)
        is CurrentUserUiState.Error -> ErrorScreen(
            modifier = modifier,
            errorMessage = (uiState as CurrentUserUiState.Error).errorMessage,
            onClick = {}
        )

        is CurrentUserUiState.Content -> {
            LoadedCurrentUserScreen(
                uiState = uiState as CurrentUserUiState.Content,
                postsUiState = postsUiState,
                hasUserSubscription = hasUserSubscription,
                modifier = modifier,
                onEvent = viewModel::onEvent
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LoadedCurrentUserScreen(
    uiState: CurrentUserUiState.Content,
    postsUiState: CurrentUserPostsUiState,
    hasUserSubscription: Boolean,
    onEvent: (CurrentUserEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val tabs = postsUiState.profileTabs(onEvent = onEvent)
    val pagerState = rememberPagerState { tabs.size }
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            AnimateSlideTop(
                isVisible = scrollState.value > (scrollState.maxValue * 0.8)
            ) {
                AppTopBar(title = "Joseph")
            }
        }
    ) {
        BoxWithConstraints(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            val screenHeight = maxHeight
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(state = scrollState)
            ) {
                UserPersonalInfo(
                    user = uiState.user,
                    hasUserSubscription = hasUserSubscription,
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
                                    .clip(RoundedCornerShape(SmallSpacing)),
                                height = FriendSyncTheme.dimens.dp2,
                                color = FriendSyncTheme.colors.primary
                            )
                        }
                    ) {
                        tabs.forEachIndexed { index, tab ->
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
                        tabs[page].content()
                    }
                }
            }
        }
    }
}

fun CurrentUserPostsUiState.profileTabs(
    onEvent: (CurrentUserEvent) -> Unit
) = listOf(
    ProfileTab(
        title = MainResStrings.posts,
        content = {
            CurrentUserPostsScreen(
                uiState = this,
                onEvent = onEvent
            )
        }
    ),
    ProfileTab(
        title = MainResStrings.stories,
        content = { EmptyScreen() }

    ),
    ProfileTab(
        title = MainResStrings.liked,
        content = { EmptyScreen() }
    ),
    ProfileTab(
        title = MainResStrings.tagged,
        content = { EmptyScreen() }
    )
)
