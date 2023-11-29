package org.joseph.friendsync.profile.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import org.koin.core.parameter.parametersOf

class ProfileScreenDestination(
    private val userId: Int = UNKNOWN_USER_ID,
) : Screen {

    @Composable
    override fun Content() {
        val viewModel = getScreenModel<ProfileViewModel> { parametersOf(userId) }
        val uiState by viewModel.state.collectAsState()
        ProfileScreen(uiState = uiState)
    }
}