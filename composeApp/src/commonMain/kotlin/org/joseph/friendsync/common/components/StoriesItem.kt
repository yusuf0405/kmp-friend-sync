package org.joseph.friendsync.common.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import com.seiko.imageloader.rememberImagePainter
import org.joseph.friendsync.common.theme.FriendSyncTheme
import org.joseph.friendsync.common.theme.dimens.ExtraLargeSpacing
import org.joseph.friendsync.common.theme.dimens.ExtraMediumSpacing
import org.joseph.friendsync.common.theme.dimens.LargeSpacing
import org.joseph.friendsync.common.theme.dimens.SmallSpacing
import org.joseph.friendsync.models.Stories

@Composable
fun StoriesList(
    storiesList: List<Stories>,
    onStoriesClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = ExtraLargeSpacing),
        horizontalArrangement = Arrangement.spacedBy(ExtraMediumSpacing)
    ) {
        items(
            items = storiesList.groupBy { it.userId }.toList(),
            key = { stories -> stories.hashCode() }
        ) { stories ->
            StoriesItem(
                stories = stories.second.first(),
                isViewed = stories.second.all { it.isViewed },
                onStoriesClick = onStoriesClick
            )
        }
    }
}

@Composable
fun StoriesItem(
    stories: Stories,
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
        val painter = rememberImagePainter(stories.imageUrl)
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
            imageUrl = stories.userImage,
            isViewed = isViewed,
        )
    }
}