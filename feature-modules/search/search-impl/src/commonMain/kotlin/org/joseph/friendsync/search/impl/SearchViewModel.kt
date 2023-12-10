package org.joseph.friendsync.search.impl

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.joseph.friendsync.common.util.coroutines.launchSafe
import org.joseph.friendsync.domain.usecases.categories.FetchAllCategoriesUseCase
import org.koin.core.component.KoinComponent
import org.joseph.friendsync.common.util.Result
import org.joseph.friendsync.core.ui.snackbar.FriendSyncSnackbar
import org.joseph.friendsync.core.ui.snackbar.SnackbarDisplay
import org.joseph.friendsync.core.ui.strings.MainResStrings
import org.joseph.friendsync.ui.components.mappers.CategoryDomainToCategoryMapper
import org.joseph.friendsync.ui.components.models.Category

class SearchViewModel(
    private val fetchAllCategoriesUseCase: FetchAllCategoriesUseCase,
    private val categoryDomainToCategoryMapper: CategoryDomainToCategoryMapper,
    private val snackbarDisplay: SnackbarDisplay,
) : StateScreenModel<SearchUiState>(SearchUiState.Initial), KoinComponent {

    private val loadedUiStateFlow = mutableState.filterIsInstance<SearchUiState.Loaded>()

    private val searchQueryFlow = loadedUiStateFlow
        .map { it.searchQueryValue }
        .stateIn(screenModelScope, SharingStarted.Eagerly, String())

    private val selectedCategoryFlow = loadedUiStateFlow
        .map { it.selectedCategory }
        .stateIn(screenModelScope, SharingStarted.Eagerly, Category.unknown)

    private var allDataJob: Job? = null

    init {
        allDataJob = loadAllData()
    }

    fun onEvent(event: SearchScreenEvent) {
        when (event) {
            is SearchScreenEvent.RefreshAllData -> refreshAllData()
            is SearchScreenEvent.OnSearchValueChange -> setSearchQuery(event.value)
            is SearchScreenEvent.OnCategoryClick -> doCategoryClick(event.category)
        }
    }

    private fun loadAllData(): Job = screenModelScope.launchSafe(
        onError = { setUiStateToError(MainResStrings.default_error_message) }
    ) {
        mutableState.tryEmit(SearchUiState.Loading)
        fetchAllCategories()
    }

    private fun refreshAllData() {
        allDataJob = null
        allDataJob = loadAllData()
    }

    private suspend fun fetchAllCategories() {
        when (val response = fetchAllCategoriesUseCase()) {
            is Result.Success -> (response.data ?: emptyList())
                .map(categoryDomainToCategoryMapper::map)
                .also(::setUiStateByReceivedCategories)

            is Result.Error -> {
                val message = response.message
                setUiStateToError(message)
                showErrorSnackbar(message)
            }
        }
    }

    private fun doCategoryClick(category: Category) {
        val uiState = mutableState.value as? SearchUiState.Loaded ?: return
        mutableState.tryEmit(uiState.copy(selectedCategory = category))
    }

    private fun setSearchQuery(value: String) {
        val uiState = mutableState.value as? SearchUiState.Loaded ?: return
        val searchQueryValue = if (value.trim().isEmpty()) value.trim() else value
        mutableState.tryEmit(uiState.copy(searchQueryValue = searchQueryValue))
    }

    private fun showErrorSnackbar(message: String?) {
        val errorMessage = message ?: MainResStrings.default_error_message
        snackbarDisplay.showSnackbar(FriendSyncSnackbar.Error(errorMessage))
    }

    private fun setUiStateByReceivedCategories(categories: List<Category>) {
        val selectedCategory = categories.firstOrNull() ?: Category.unknown
        (mutableState.value as? SearchUiState.Loaded)?.copy(
            categories = categories,
            selectedCategory = selectedCategory
        ) ?: SearchUiState.Loaded(
            categories = categories,
            selectedCategory = selectedCategory
        ).also(mutableState::tryEmit)
    }

    private fun setUiStateToError(message: String?) {
        val errorMessage = message ?: MainResStrings.default_error_message
        mutableState.tryEmit(SearchUiState.Error(errorMessage))
    }

    override fun onDispose() {
        allDataJob = null
        super.onDispose()
    }
}