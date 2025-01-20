package org.joseph.friendsync.providers

import coil3.ImageLoader
import coil3.PlatformContext

interface ImageLoaderConfigurator {

    fun configureAndGet(context: PlatformContext): ImageLoader
}