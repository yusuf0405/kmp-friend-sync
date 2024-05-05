package org.joseph.friendsync.add.post.impl.state

import androidx.compose.runtime.Immutable
import org.joseph.friendsync.domain.models.UserPreferences
import org.joseph.friendsync.core.FileProperties

@Immutable
internal data class ScreenUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val message: String? = null,
    val user: UserPreferences = UserPreferences.unknown,
    val filePropertiesList: List<FileProperties> = emptyList(),
) {
    val images = filePropertiesList.mapNotNull { it.imageBitmap }

    fun addFileProperties(fileProperties: FileProperties?): ScreenUiState {
        val filePropertiesList = filePropertiesList.toMutableList()
        return if (fileProperties == null) {
            this
        } else {
            copy(
                filePropertiesList = filePropertiesList
            )
        }
    }
}