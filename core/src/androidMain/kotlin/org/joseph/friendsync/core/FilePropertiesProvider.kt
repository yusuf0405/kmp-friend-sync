package org.joseph.friendsync.core

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.compose.ui.graphics.asImageBitmap
import java.io.ByteArrayOutputStream

actual class FilePropertiesProvider actual constructor(
    private val platformConfiguration: PlatformConfiguration
) {
    actual fun get(platformFile: Any): FileProperties? {
        return try {
            val uri = platformFile as? Uri ?: return null
            val contentResolver = platformConfiguration.androidContext.contentResolver
            val bitmap = if (Build.VERSION.SDK_INT < 28) {
                MediaStore.Images.Media.getBitmap(contentResolver, uri)
            } else {
                val source = ImageDecoder.createSource(contentResolver, uri)
                ImageDecoder.decodeBitmap(source)
            }
            FileProperties(
                byteArray = bitmap.toByteArray(),
                imageBitmap = bitmap.asImageBitmap()
            )
        } catch (e: Exception) {
            null
        }
    }

    private fun Bitmap.toByteArray(
        format: Bitmap.CompressFormat = Bitmap.CompressFormat.PNG,
        quality: Int = 100
    ): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        compress(format, quality, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }
}