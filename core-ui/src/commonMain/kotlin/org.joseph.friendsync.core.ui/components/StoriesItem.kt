package org.joseph.friendsync.core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import com.seiko.imageloader.rememberImagePainter
import org.joseph.friendsync.core.ui.theme.FriendSyncTheme
import org.joseph.friendsync.core.ui.theme.dimens.LargeSpacing
import org.joseph.friendsync.core.ui.theme.dimens.SmallSpacing

@Composable
fun StoriesItem(
    avatar: String,
    imageUrl: String,
    isViewed: Boolean,
    onStoriesClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .width(FriendSyncTheme.dimens.storiesItemWidth)
            .height(FriendSyncTheme.dimens.storiesItemHeight)
            .clip(RoundedCornerShape(LargeSpacing))
            .clickable { onStoriesClick() }
    ) {
        val painter = rememberImagePainter(imageUrl)
        Image(
            modifier = Modifier
                .fillMaxSize()
                .background(Placeholder()),
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        CircularBorderImage(
            modifier = Modifier
                .padding(bottom = SmallSpacing)
                .align(Alignment.BottomCenter),
            imageModifier = Modifier.size(FriendSyncTheme.dimens.dp40),
            imageUrl = avatar,
            isViewed = isViewed,
        )
    }
}