package org.joseph.friendsync.providers

import coil3.ImageLoader
import coil3.PlatformContext
import coil3.disk.DiskCache
import coil3.memory.MemoryCache
import coil3.request.CachePolicy
import coil3.request.crossfade
import coil3.util.DebugLogger
import okio.FileSystem
import org.koin.core.component.KoinComponent

private const val CACHE_FILE_NAME = "image_cache"

// 512MB
private const val MAX_BYTES_SIZE = 1024L * 1024 * 1024

class ImageLoaderDefaultConfigurator : ImageLoaderConfigurator, KoinComponent {

    override fun configureAndGet(context: PlatformContext) = ImageLoader.Builder(context)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .memoryCache { context.newMemoryCache() }
        .diskCachePolicy(CachePolicy.ENABLED)
        .networkCachePolicy(CachePolicy.ENABLED)
        .diskCache { newDiskCache() }
        .crossfade(true)
        .logger(DebugLogger())
        .build()

    private fun PlatformContext.newMemoryCache() = MemoryCache.Builder()
        .maxSizePercent(this, 0.3)
        .strongReferencesEnabled(true)
        .build()

    private fun newDiskCache(): DiskCache = DiskCache.Builder()
        .directory(FileSystem.SYSTEM_TEMPORARY_DIRECTORY / CACHE_FILE_NAME)
        .maxSizeBytes(MAX_BYTES_SIZE)
        .build()
}