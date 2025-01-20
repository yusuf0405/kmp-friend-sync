package org.joseph.friendsync.search.impl

import androidx.compose.runtime.Composable
import org.joseph.friendsync.ui.components.models.Category
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

sealed interface ErrorMessage {
    data class String(val message: kotlin.String) : ErrorMessage
    data class Resource(val message: StringResource) : ErrorMessage

    @Composable
    fun get(): kotlin.String {
        return when (this) {
            is String -> this.message
            is Resource -> stringResource(this.message)
        }
    }
}

sealed class SearchUiState {

    data object Initial : SearchUiState()

    data object Loading : SearchUiState()

    data class Error(
        val message: ErrorMessage
    ) : SearchUiState()

    data class Loaded(
        val categories: List<Category>,
        val searchQueryValue: String = String(),
        val selectedCategory: Category = Category.unknown,
    ) : SearchUiState()
}