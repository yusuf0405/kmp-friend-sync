package org.joseph.friendsync.search.impl

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AllInbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.joseph.friendsync.core.ui.common.ErrorScreen
import org.joseph.friendsync.core.ui.common.LoadingScreen
import org.joseph.friendsync.core.ui.components.CategoryItem
import org.joseph.friendsync.core.ui.components.PostItem
import org.joseph.friendsync.core.ui.components.SearchTextField
import org.joseph.friendsync.core.ui.components.UserItemWithName
import org.joseph.friendsync.core.ui.components.UserVerticalItem
import org.joseph.friendsync.core.ui.extensions.SpacerHeight
import org.joseph.friendsync.core.ui.strings.MainResStrings
import org.joseph.friendsync.core.ui.theme.FriendSyncTheme
import org.joseph.friendsync.core.ui.theme.dimens.ExtraLargeSpacing
import org.joseph.friendsync.core.ui.theme.dimens.ExtraMediumSpacing
import org.joseph.friendsync.core.ui.theme.dimens.LargeSpacing
import org.joseph.friendsync.core.ui.theme.dimens.MediumSpacing
import org.joseph.friendsync.search.impl.category.CategoryType
import org.joseph.friendsync.search.impl.post.PostUiState
import org.joseph.friendsync.search.impl.user.UsersUiState
import org.joseph.friendsync.ui.components.models.Category
import org.joseph.friendsync.ui.components.models.user.UserInfo

@Composable
fun SearchScreen(
    uiState: SearchUiState,
    postUiState: PostUiState,
    userUiState: UsersUiState,
    selectedCategoryType: CategoryType,
    onEvent: (SearchScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiState) {
        is SearchUiState.Initial -> Unit
        is SearchUiState.Error -> ErrorScreen(
            errorMessage = uiState.message,
            onClick = { onEvent(SearchScreenEvent.RefreshAllData) }
        )

        is SearchUiState.Loading, is SearchUiState.Loaded -> SearchScreenContent(
            uiState = uiState,
            postUiState = postUiState,
            userUiState = userUiState,
            selectedCategoryType = selectedCategoryType,
            onEvent = onEvent,
            modifier = modifier
        )
    }
}

@Composable
fun SearchScreenContent(
    uiState: SearchUiState,
    postUiState: PostUiState,
    userUiState: UsersUiState,
    selectedCategoryType: CategoryType,
    onEvent: (SearchScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val searchQueryValue = (uiState as? SearchUiState.Loaded)?.searchQueryValue.orEmpty()
    val isLoadingAllData = isLoadingAllData(selectedCategoryType, postUiState, userUiState)
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(FriendSyncTheme.colors.backgroundPrimary),
    ) {
        item {
            SearchTextField(
                modifier = Modifier.padding(ExtraLargeSpacing),
                placeholder = MainResStrings.search_screen_placeholder,
                value = searchQueryValue,
                onValueChange = { onEvent(SearchScreenEvent.OnSearchValueChange(it)) }
            )
        }

        when (uiState) {
            is SearchUiState.Initial -> Unit
            is SearchUiState.Error -> Unit
            is SearchUiState.Loading -> item { LoadingScreen() }
            is SearchUiState.Loaded -> {
                item {
                    Text(
                        modifier = Modifier.padding(horizontal = ExtraLargeSpacing),
                        text = MainResStrings.popular,
                        style = FriendSyncTheme.typography.bodyExtraLarge.bold,
                    )
                    Spacer(Modifier.height(ExtraMediumSpacing))
                    CategoryList(
                        categories = uiState.categories,
                        selectedCategory = uiState.selectedCategory,
                        onClick = { onEvent(SearchScreenEvent.OnCategoryClick(it)) }
                    )
                }
                item { Spacer(modifier = Modifier.height(LargeSpacing)) }
                showUsersContent(selectedCategoryType, userUiState, onEvent)
                showPostsContent(selectedCategoryType, postUiState, isLoadingAllData, onEvent)
            }
        }
    }
}

private fun LazyListScope.showUsersContent(
    selectedCategoryType: CategoryType,
    userUiState: UsersUiState,
    onEvent: (SearchScreenEvent) -> Unit,
) {
    if (!shouldShowUsers(selectedCategoryType)) return

    when (userUiState) {
        is UsersUiState.Initial -> Unit
        is UsersUiState.Loading -> item {
            Box(
                modifier = Modifier
                    .padding(ExtraLargeSpacing)
                    .fillParentMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is UsersUiState.Empty -> {
            if (selectedCategoryType == CategoryType.PROFILES) {
                item { EmptyScreen() }
            }
        }

        is UsersUiState.Error -> item {
            ErrorScreen(userUiState.message, onClick = {})
        }

        is UsersUiState.Loaded -> {
            if (selectedCategoryType == CategoryType.ALL) {
                item {
                    if (userUiState.users.isNotEmpty()) {
                        UserInfoHorizontalList(
                            users = userUiState.users,
                            onClick = { onEvent(SearchScreenEvent.OnProfileClick(it)) }
                        )
                    }
                }
            }

            if (selectedCategoryType == CategoryType.PROFILES) {
                itemsIndexed(
                    items = userUiState.users,
                    key = { _, item -> item.id }
                ) { index, user ->
                    UserVerticalItem(
                        avatarUrl = user.avatar,
                        name = user.name,
                        lastName = user.lastName,
                        imageSize = FriendSyncTheme.dimens.dp80,
                        onClick = { onEvent(SearchScreenEvent.OnProfileClick(user.id)) }
                    )
                    if (index >= userUiState.users.size - 1 && !userUiState.isPaging) {
                        LaunchedEffect(key1 = Unit, block = {})
                    }
                }

                if (userUiState.isPaging && userUiState.users.isNotEmpty()) {
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
    }
}

private fun LazyListScope.showPostsContent(
    selectedCategoryType: CategoryType,
    postUiState: PostUiState,
    isLoadingAllData: Boolean,
    onEvent: (SearchScreenEvent) -> Unit,
) {
    if (!shouldShowPosts(selectedCategoryType)) return
    when (postUiState) {
        is PostUiState.Initial -> Unit
        is PostUiState.Loading -> {
            if (!isLoadingAllData) {
                item {
                    Box(
                        modifier = Modifier
                            .padding(ExtraLargeSpacing)
                            .fillParentMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }

        is PostUiState.Empty -> item { EmptyScreen() }

        is PostUiState.Error -> item {
            ErrorScreen(postUiState.message, onClick = {})
        }

        is PostUiState.Loaded -> {
            itemsIndexed(
                items = postUiState.posts,
                key = { _, item -> item.id }
            ) { index, post ->
                PostItem(
                    post = post,
                    imageUrls = post.imageUrls,
                    commentCount = post.commentCount,
                    likesCount = post.likedCount,
                    onPostClick = { onEvent(SearchScreenEvent.OnPostClick(post.id)) },
                    onProfileClick = { onEvent(SearchScreenEvent.OnProfileClick(post.authorId)) },
                    onLikeClick = { },
                    onCommentClick = { onEvent(SearchScreenEvent.OnCommentClick(post.id)) },
                )
                if (index >= postUiState.posts.size - 1 && !postUiState.isPaging) {
                    LaunchedEffect(key1 = Unit, block = {})
                }
            }

            if (postUiState.isPaging && postUiState.posts.isNotEmpty()) {
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
}

@Composable
private fun EmptyScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(FriendSyncTheme.dimens.dp36)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier.size(FriendSyncTheme.dimens.dp68),
            imageVector = Icons.Default.AllInbox,
            contentDescription = null,
            tint = FriendSyncTheme.colors.iconsSecondary
        )
        SpacerHeight(LargeSpacing)
        Text(
            text = MainResStrings.empty_data,
            style = FriendSyncTheme.typography.titleMedium.extraBold,
            color = FriendSyncTheme.colors.textSecondary
        )
    }
}

@Composable
private fun CategoryList(
    categories: List<Category>,
    selectedCategory: Category,
    onClick: (Category) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = ExtraLargeSpacing),
        horizontalArrangement = Arrangement.spacedBy(ExtraMediumSpacing)
    ) {
        items(
            items = categories,
            key = { item -> item.id }
        ) { category ->
            val isSelected = category == selectedCategory
            CategoryItem(
                name = category.name,
                isSelected = isSelected,
                onClick = { onClick(category) }
            )
        }
    }
}

@Composable
private fun UserInfoHorizontalList(
    users: List<UserInfo>,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(
            bottom = ExtraLargeSpacing,
            start = ExtraLargeSpacing,
            end = ExtraLargeSpacing,
            top = MediumSpacing,
        ),
        horizontalArrangement = Arrangement.spacedBy(ExtraLargeSpacing + MediumSpacing)
    ) {
        items(
            items = users,
            key = { item -> item.id }
        ) { user ->
            UserItemWithName(
                avatarUrl = user.avatar,
                name = user.name,
                imageSize = FriendSyncTheme.dimens.dp68,
                onClick = { onClick(user.id) }
            )
        }
    }
}

private fun shouldShowUsers(selectedCategoryType: CategoryType): Boolean {
    return selectedCategoryType == CategoryType.ALL || selectedCategoryType == CategoryType.PROFILES
}

private fun shouldShowPosts(selectedCategoryType: CategoryType): Boolean {
    return selectedCategoryType == CategoryType.ALL
            || selectedCategoryType == CategoryType.PHOTOS
            || selectedCategoryType == CategoryType.TEXT
}

private fun isLoadingAllData(
    selectedCategoryType: CategoryType,
    postUiState: PostUiState,
    userUiState: UsersUiState
): Boolean {
    return selectedCategoryType == CategoryType.ALL
            && postUiState == PostUiState.Loading
            && userUiState == UsersUiState.Loading
}