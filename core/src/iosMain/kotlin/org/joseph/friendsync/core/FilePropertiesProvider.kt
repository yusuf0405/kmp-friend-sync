package org.joseph.friendsync.core

import androidx.compose.ui.graphics.toComposeImageBitmap
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import org.jetbrains.skia.Image
import platform.Foundation.NSData
import platform.Foundation.NSURL
import platform.posix.memcpy

actual class FilePropertiesProvider actual constructor(platformConfiguration: PlatformConfiguration) {

    actual fun get(platformFile: Any): FileProperties? {
        return try {
            val uri = platformFile as? NSURL ?: return null
            val encodedImageData = uri.dataRepresentation.toByteArray()
            val imageBitmap = Image.makeFromEncoded(encodedImageData).toComposeImageBitmap()
            FileProperties(
                byteArray = encodedImageData,
                imageBitmap = imageBitmap
            )
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