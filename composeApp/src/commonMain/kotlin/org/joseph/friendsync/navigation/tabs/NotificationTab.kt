package org.joseph.friendsync.navigation.tabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.NotificationsOff
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import compose.icons.FeatherIcons
import compose.icons.feathericons.Home
import io.github.skeptick.libres.compose.painterResource
import org.joseph.friendsync.images.MainResImages
import org.joseph.friendsync.navigation.TabContent
import org.joseph.friendsync.strings.MainResStrings

object NotificationTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = MainResStrings.home_destination_title
            val imageVector = rememberVectorPainter(Icons.Outlined.Notifications)

            return remember {
                TabOptions(
                    index = 3u,
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