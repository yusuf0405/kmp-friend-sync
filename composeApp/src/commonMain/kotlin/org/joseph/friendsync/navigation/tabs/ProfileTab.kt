package org.joseph.friendsync.navigation.tabs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import compose.icons.FeatherIcons
import compose.icons.feathericons.User
import org.joseph.friendsync.navigation.TabContent
import org.joseph.friendsync.core.ui.strings.MainResStrings

object ProfileTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = MainResStrings.home_destination_title
            val imageVector = rememberVectorPainter(FeatherIcons.User)

            return remember {
                TabOptions(
                    index = 4u,
                    title = title,
                    icon = imageVector
                )
            }
        }

    @Composable
    override fun Content() {
        TabContent()
    }
}