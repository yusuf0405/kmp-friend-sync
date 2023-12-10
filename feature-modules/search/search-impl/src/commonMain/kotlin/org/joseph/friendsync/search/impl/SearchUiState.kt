package org.joseph.friendsync.search.impl

import org.joseph.friendsync.models.Category

sealed class SearchUiState {

    data object Initial : SearchUiState()

    data object Loading : SearchUiState()

    data class Error(
        val message: String
    ) : SearchUiState()

    data class Loaded(
        val categories: List<Category>,
        val searchQueryValue: String = String(),
        val selectedCategory: Category = Category.unknown,
    ) : SearchUiState()
}