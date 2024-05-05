package org.joseph.friendsync.add.post.impl

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Photo
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import kotlinx.coroutines.launch
import org.joseph.friendsync.add.post.impl.model.FileType
import org.joseph.friendsync.add.post.impl.state.ScreenUiState
import org.joseph.friendsync.core.ui.components.CircularImage
import org.joseph.friendsync.core.ui.components.HorizontalPagerIndicator
import org.joseph.friendsync.core.ui.extensions.SpacerHeight
import org.joseph.friendsync.core.ui.extensions.SpacerWidth
import org.joseph.friendsync.core.ui.strings.MainResStrings
import org.joseph.friendsync.core.ui.theme.FriendSyncTheme
import org.joseph.friendsync.core.ui.theme.dimens.ExtraLargeSpacing
import org.joseph.friendsync.core.ui.theme.dimens.ExtraMediumSpacing
import org.joseph.friendsync.core.ui.theme.dimens.LargeSpacing
import org.joseph.friendsync.core.ui.theme.dimens.MediumSpacing

@Composable
internal fun AddPostScreen(
    uiState: ScreenUiState,
    onAction: (ScreenAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(FriendSyncTheme.colors.backgroundPrimary),
    ) {
        SpacerHeight(ExtraLargeSpacing)
        Row(
            modifier = Modifier
                .padding(horizontal = ExtraLargeSpacing)
                .fillMaxWidth()
        ) {
            CircularImage(
                imageUrl = uiState.user.avatar,
                modifier = Modifier
                    .padding(top = ExtraMediumSpacing)
                    .size(FriendSyncTheme.dimens.dp32)
            )
            TextField(
                value = uiState.message ?: String(),
                onValueChange = { onAction(ScreenAction.OnMessageChange(it)) },
                placeholder = {
                    Text(
                        text = MainResStrings.whats_on_your_mind,
                        style = FriendSyncTheme.typography.bodyMedium.medium,
                    )
                },
                colors = TextFieldDefaults.colors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedTextColor = FriendSyncTheme.colors.textPrimary
                ),
                textStyle = FriendSyncTheme.typography.bodyMedium.medium,
            )
        }
        SpacerHeight(MediumSpacing)
        PhotoContent(
            uiState = uiState,
            onAction = onAction
        )
    }
}

@Composable
private fun PhotoContent(
    uiState: ScreenUiState,
    onAction: (ScreenAction) -> Unit,
) {
    var showFilePicker by remember { mutableStateOf(false) }
    var isShowCloseIcon by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val fileType = FileType.entries.map { it.name }

    FilePicker(
        show = showFilePicker,
        fileExtensions = fileType
    ) { file ->
        file?.let {
            scope.launch { onAction(ScreenAction.OnImageChange(file.platformFile)) }
        }
        showFilePicker = false
    }

    val icon = if (isShowCloseIcon) Icons.Default.Close
    else Icons.Default.Add

    Row(
        modifier = Modifier.padding(horizontal = ExtraLargeSpacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .padding(vertical = MediumSpacing)
                .clip(CircleShape)
                .border(
                    width = FriendSyncTheme.dimens.dp1,
                    color = FriendSyncTheme.colors.iconsSecondary,
                    shape = CircleShape
                )
                .size(FriendSyncTheme.dimens.dp32)
                .clickable { isShowCloseIcon = isShowCloseIcon.not() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(LargeSpacing),
                imageVector = icon,
                contentDescription = null,
                tint = FriendSyncTheme.colors.iconsPrimary
            )
        }
        SpacerWidth(ExtraMediumSpacing)
        AnimatedVisibility(visible = isShowCloseIcon) {
            Row(
                modifier = Modifier
                    .clip(CircleShape)
                    .border(
                        width = FriendSyncTheme.dimens.dp1,
                        color = FriendSyncTheme.colors.iconsSecondary,
                        shape = CircleShape
                    )
                    .background(FriendSyncTheme.colors.backgroundPrimary)
                    .padding(vertical = MediumSpacing, horizontal = ExtraMediumSpacing),
                horizontalArrangement = Arrangement.spacedBy(LargeSpacing),
            ) {
                Icon(
                    modifier = Modifier.clickable { showFilePicker = true },
                    imageVector = Icons.Outlined.Photo,
                    contentDescription = null
                )
//                Icon(
//                    imageVector = Icons.Outlined.Gif,
//                    contentDescription = null
//                )
//                Icon(
//                    modifier = Modifier.clickable {
//
//                    },
//                    imageVector = Icons.Outlined.PhotoCamera,
//                    contentDescription = null
//                )
//                Icon(
//                    imageVector = Icons.Outlined.Attachment,
//                    contentDescription = null
//                )
            }
        }
    }
    Spacer(modifier = Modifier.height(ExtraLargeSpacing + ExtraMediumSpacing))
    PostPhotoPager(uiState.images)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PostPhotoPager(
    images: List<ImageBitmap>,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState { images.size }

    Column(
        modifier = modifier
            .padding(top = LargeSpacing)
            .padding(bottom = ExtraLargeSpacing + ExtraLargeSpacing)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        HorizontalPager(
            modifier = modifier,
            state = pagerState
        ) { index ->
            PostImagePage(
                pagerState = pagerState,
                index = index,
                imageBitmap = images[index]
            )
        }

        if (images.size > 1) {
            Box(
                modifier = Modifier
                    .padding(top = LargeSpacing)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                HorizontalPagerIndicator(
                    modifier = Modifier,
                    pagerState = pagerState,
                    pageCount = images.size,
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PostImagePage(
    imageBitmap: ImageBitmap,
    index: Int,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
) {

    val matrix = remember { ColorMatrix() }
    val pagerOffset =
        (pagerState.currentPage - index) + pagerState.currentPageOffsetFraction

    val imageSize by animateFloatAsState(
        targetValue = if (pagerOffset != 0.0f) 0.75f else 1f,
        animationSpec = tween(durationMillis = 300), label = ""
    )

    LaunchedEffect(key1 = imageSize) {
        val saturation = if (pagerOffset != 0.0f) 0f
        else 1f
        matrix.setToSaturation(saturation)
    }

    Image(
        bitmap = imageBitmap,
        modifier = modifier
            .padding(horizontal = ExtraLargeSpacing + MediumSpacing)
            .clip(RoundedCornerShape(LargeSpacing))
            .fillMaxWidth()
            .graphicsLayer {
                scaleX = imageSize
                scaleY = imageSize
                shape = RoundedCornerShape(LargeSpacing)
                clip = true
            },
        contentDescription = null,
        contentScale = ContentScale.Crop,
        colorFilter = ColorFilter.colorMatrix(matrix),
    )
}