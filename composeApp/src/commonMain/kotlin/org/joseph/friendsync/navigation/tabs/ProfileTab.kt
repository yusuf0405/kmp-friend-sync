package org.joseph.friendsync.navigation.tabs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import io.github.skeptick.libres.compose.painterResource
import org.joseph.friendsync.images.MainResImages
import org.joseph.friendsync.navigation.TabContent
import org.joseph.friendsync.strings.MainResStrings

object ProfileTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = MainResStrings.home_destination_title
            val icon = painterResource(MainResImages.profile_tab_icon)

            return remember {
                TabOptions(
                    index = 4u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        TabContent()
    }
}