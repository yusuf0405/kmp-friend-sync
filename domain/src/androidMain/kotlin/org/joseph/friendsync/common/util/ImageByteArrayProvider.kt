package org.joseph.friendsync.common.util

import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import org.joseph.friendsync.PlatformConfiguration
import java.io.ByteArrayOutputStream

actual class ImageByteArrayProvider actual constructor(
    private val platformConfiguration: PlatformConfiguration
) {

    actual fun fetchImageBitmap(platformFile: Any): Pair<ByteArray?, ImageBitmap?>? {
        return try {
            val uri = platformFile as? Uri ?: return null
            val contentResolver = platformConfiguration.androidContext.contentResolver
            val bitmap = if (Build.VERSION.SDK_INT < 28) {
                MediaStore.Images.Media.getBitmap(contentResolver, uri)
            } else {
                val source = ImageDecoder.createSource(contentResolver, uri)
                ImageDecoder.decodeBitmap(source)
            }
            Pair(bitmapToByteArray(bitmap), bitmap.asImageBitmap())
        } catch (e: Exception) {
            null
        }
    }

    private fun bitmapToByteArray(
        bitmap: Bitmap,
        format: CompressFormat = CompressFormat.PNG,
        quality: Int = 100
    ): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(format, quality, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }
}