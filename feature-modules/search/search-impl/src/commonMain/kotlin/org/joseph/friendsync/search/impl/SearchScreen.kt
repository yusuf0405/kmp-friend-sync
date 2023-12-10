package org.joseph.friendsync.search.impl

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.joseph.friendsync.core.ui.common.ErrorScreen
import org.joseph.friendsync.core.ui.common.LoadingScreen
import org.joseph.friendsync.core.ui.components.CategoryItem
import org.joseph.friendsync.core.ui.components.SearchTextField
import org.joseph.friendsync.core.ui.strings.MainResStrings
import org.joseph.friendsync.core.ui.theme.FriendSyncTheme
import org.joseph.friendsync.core.ui.theme.dimens.ExtraLargeSpacing
import org.joseph.friendsync.core.ui.theme.dimens.ExtraMediumSpacing
import org.joseph.friendsync.ui.components.models.Category

@Composable
fun SearchScreen(
    uiState: SearchUiState,
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
            onEvent = onEvent,
            modifier = modifier
        )
    }
}

@Composable
fun SearchScreenContent(
    uiState: SearchUiState,
    onEvent: (SearchScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val searchQueryValue = (uiState as? SearchUiState.Loaded)?.searchQueryValue ?: String()

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
        if (uiState is SearchUiState.Loading) {
            item { LoadingScreen() }
        }
        if (uiState is SearchUiState.Loaded) {
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
        }
    }
}

@Composable
fun CategoryList(
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