package org.joseph.friendsync.common.util

import androidx.compose.ui.graphics.ImageBitmap
import org.joseph.friendsync.PlatformConfiguration

expect class ImageByteArrayProvider constructor(
    platformConfiguration: PlatformConfiguration
) {

    fun fetchImageBitmap(platformFile: Any): Pair<ByteArray?, ImageBitmap?>?
}