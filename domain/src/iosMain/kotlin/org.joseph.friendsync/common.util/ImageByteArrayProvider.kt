package org.joseph.friendsync.common.util


import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import org.jetbrains.skia.Image
import org.joseph.friendsync.PlatformConfiguration
import platform.Foundation.NSData
import platform.Foundation.NSURL
import platform.posix.memcpy

actual class ImageByteArrayProvider actual constructor(platformConfiguration: PlatformConfiguration) {

    actual fun fetchImageBitmap(platformFile: Any): Pair<ByteArray?, ImageBitmap?>? {
        return try {
            val uri = platformFile as? NSURL ?: return null
            val encodedImageData = uri.dataRepresentation.toByteArray()
            val imageBitmap = Image.makeFromEncoded(encodedImageData).toComposeImageBitmap()
            Pair(encodedImageData, imageBitmap)
        } catch (e: Exception) {
            null
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    fun NSData.toByteArray(): ByteArray = ByteArray(this@toByteArray.length.toInt()).apply {
        usePinned {
            memcpy(it.addressOf(0), this@toByteArray.bytes, this@toByteArray.length)
        }
    }
}