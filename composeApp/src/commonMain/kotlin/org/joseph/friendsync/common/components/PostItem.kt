package org.joseph.friendsync.common.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.rememberImagePainter
import compose.icons.FeatherIcons
import compose.icons.feathericons.MoreVertical
import org.joseph.friendsync.common.extensions.SpacerHeight
import org.joseph.friendsync.common.extensions.clickableNoRipple
import org.joseph.friendsync.common.theme.FriendSyncTheme
import org.joseph.friendsync.common.theme.dimens.LargeSpacing
import org.joseph.friendsync.common.theme.dimens.MediumSpacing
import org.joseph.friendsync.common.theme.dimens.SmallSpacing
import org.joseph.friendsync.models.Post

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhotoPostItem(
    modifier: Modifier = Modifier,
    post: Post.PhotoPost,
    onPostClick: (Int) -> Unit,
    onProfileClick: (Int) -> Unit = {},
    onLikeClick: () -> Unit = {},
    onCommentClick: (Int) -> Unit = {},
    isDetailScreen: Boolean = false
) {
    val pagerState = rememberPagerState {
        post.imageUrls.size
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickableNoRipple { onPostClick(post.id) },
    ) {
        if (!isDetailScreen) Divider()
        SpacerHeight(SmallSpacing)
        PostItemHeader(
            name = post.authorName,
            profileUrl = post.authorImage,
            date = post.createdAt,
            onProfileClick = { onProfileClick(post.authorId) }
        )
        HorizontalPager(
            state = pagerState
        ) { index ->
            val painter = rememberImagePainter(post.imageUrls[index])
            Image(
                painter = painter,
                contentDescription = null,
                modifier = modifier
                    .padding(vertical = MediumSpacing)
                    .padding(horizontal = LargeSpacing)
                    .fillMaxWidth()
                    .aspectRatio(ratio = 1.0f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Placeholder()),
                contentScale = ContentScale.Crop,
            )
        }
        if (post.imageUrls.size > 1) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                HorizontalPagerIndicator(
                    modifier = Modifier,
                    pagerState = pagerState,
                    pageCount = post.imageUrls.size,
                )
            }
        }
        PostLikesRow(
            modifier = Modifier.padding(horizontal = LargeSpacing),
            likesCount = post.likesCount,
            commentsCount = post.commentCount,
            onLikeClick = onLikeClick,
            onCommentClick = { onCommentClick(post.id) }
        )
        Text(
            text = post.text,
            style = FriendSyncTheme.typography.bodyMedium.medium,
            modifier = modifier.padding(horizontal = LargeSpacing),
            maxLines = if (isDetailScreen) 20 else 2,
            overflow = TextOverflow.Ellipsis,
            color = FriendSyncTheme.colors.textSecondary
        )
    }
}

@Composable
fun TextPostItem(
    modifier: Modifier = Modifier,
    post: Post.TextPost,
    onPostClick: (Int) -> Unit,
    onProfileClick: (Int) -> Unit = {},
    onLikeClick: () -> Unit = {},
    onCommentClick: (Int) -> Unit = {},
    isDetailScreen: Boolean = false
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickableNoRipple { onPostClick(post.id) },
    ) {
        if (!isDetailScreen) Divider()
        SpacerHeight(SmallSpacing)
        PostItemHeader(
            name = post.authorName,
            profileUrl = post.authorImage,
            date = post.createdAt,
            onProfileClick = { onProfileClick(post.authorId) }
        )
        SpacerHeight(MediumSpacing)
        Text(
            modifier = modifier
                .padding(horizontal = LargeSpacing + MediumSpacing),
            text = post.text,
            style = FriendSyncTheme.typography.bodyExtraMedium.medium,
            maxLines = if (isDetailScreen) Int.MAX_VALUE else 9,
            overflow = TextOverflow.Ellipsis,
            color = FriendSyncTheme.colors.textPrimary
        )
        PostLikesRow(
            modifier = Modifier.padding(horizontal = LargeSpacing),
            likesCount = post.likesCount,
            commentsCount = post.commentCount,
            onLikeClick = onLikeClick,
            onCommentClick = { onCommentClick(post.id) }
        )
    }
}

@Composable
fun PostItemHeader(
    modifier: Modifier = Modifier,
    name: String,
    profileUrl: String,
    date: String,
    onProfileClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = LargeSpacing,
                vertical = MediumSpacing
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MediumSpacing)
    ) {
        CircularImage(
            modifier = modifier.size(FriendSyncTheme.dimens.dp30),
            imageUrl = profileUrl,
            onClick = onProfileClick
        )
        Text(
            text = name,
            style = FriendSyncTheme.typography.bodyMedium.regular,
            modifier = modifier.clickable { onProfileClick() },
            color = FriendSyncTheme.colors.textPrimary
        )
        Box(
            modifier = modifier
                .size(FriendSyncTheme.dimens.dp4)
                .clip(CircleShape)
                .background(
                    color = if (FriendSyncTheme.colors.isDark) Color.DarkGray
                    else Color.LightGray
                )
        )
        Text(
            text = date,
            style = FriendSyncTheme.typography.bodySmall.regular,
            textAlign = TextAlign.Start,
            color = FriendSyncTheme.colors.textSecondary,
            modifier = modifier.weight(1f)
        )

        Icon(
            imageVector = FeatherIcons.MoreVertical,
            contentDescription = null,
            tint = FriendSyncTheme.colors.iconsSecondary
        )
    }
}


@Composable
fun PostLikesRow(
    modifier: Modifier = Modifier,
    likesCount: Int,
    commentsCount: Int,
    onLikeClick: () -> Unit,
    onCommentClick: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onLikeClick) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.Outlined.FavoriteBorder,
                contentDescription = null,
                tint = FriendSyncTheme.colors.iconsSecondary
            )
        }
        Text(
            text = likesCount.toString(),
            style = FriendSyncTheme.typography.bodyExtraLarge.medium,
            color = FriendSyncTheme.colors.textPrimary
        )
        Spacer(modifier = Modifier.width(MediumSpacing))

        IconButton(onClick = onCommentClick) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.Outlined.Chat,
                contentDescription = null,
                tint = FriendSyncTheme.colors.iconsSecondary
            )
        }
        Text(
            text = commentsCount.toString(),
            style = FriendSyncTheme.typography.bodyExtraLarge.medium,
            color = FriendSyncTheme.colors.textPrimary
        )
    }
}