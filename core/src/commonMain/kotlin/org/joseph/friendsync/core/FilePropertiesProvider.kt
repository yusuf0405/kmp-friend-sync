package org.joseph.friendsync.core

import androidx.compose.ui.graphics.ImageBitmap

expect class FilePropertiesProvider constructor(
    platformConfiguration: PlatformConfiguration
) {
    fun get(platformFile: Any): FileProperties?
}

data class FileProperties(
    val byteArray: ByteArray?,
    val imageBitmap: ImageBitmap?
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as FileProperties

        if (byteArray != null) {
            if (other.byteArray == null) return false
            if (!byteArray.contentEquals(other.byteArray)) return false
        } else if (other.byteArray != null) return false
        if (imageBitmap != other.imageBitmap) return false

        return true
    }

    override fun hashCode(): Int {
        var result = byteArray?.contentHashCode() ?: 0
        result = 31 * result + (imageBitmap?.hashCode() ?: 0)
        return result
    }
}