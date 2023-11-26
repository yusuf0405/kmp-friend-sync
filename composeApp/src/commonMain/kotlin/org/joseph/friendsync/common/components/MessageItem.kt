package org.joseph.friendsync.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.joseph.friendsync.common.theme.FriendSyncTheme
import org.joseph.friendsync.common.theme.colors.LargeBlue
import org.joseph.friendsync.common.theme.dimens.ExtraLargeSpacing
import org.joseph.friendsync.common.theme.dimens.LargeSpacing
import org.joseph.friendsync.common.theme.dimens.MediumSpacing
import org.joseph.friendsync.common.theme.dimens.SmallSpacing
import org.joseph.friendsync.models.Message

@Composable
fun MessageItemList(
    messages: List<Message>,
    modifier: Modifier = Modifier,
) {
    val filtratedMessages = messages.groupBy { it.releaseDate }.toList()

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(ExtraLargeSpacing),
    ) {
        filtratedMessages.forEach { (releaseDate, messages) ->
            item {
                MessageHeader(releaseDate)
            }
            val yourMassages = messages
                .filter { it.isYouMessage }
                .groupBy { it.releaseTime }
                .toList()
                .map { Pair(true, it) }

            val userMassages = messages
                .filter { !it.isYouMessage }
                .groupBy { it.releaseTime }
                .toList()
                .map { Pair(false, it) }

            val sortedMessages = (yourMassages + userMassages)
                .toList()
                .sortedBy { it.second.first }

            sortedMessages.forEach { (isYouMessage, params) ->
                val (releaseDate, messages) = params
                items(
                    items = messages
                ) { message ->
                    if (message.isYouMessage) {
                        YourMessage(
                            message = message.message,
                        )
                    } else {
                        UserMessage(
                            message = message.message,
                            userAvatar = message.userImage
                        )
                    }
                }
                item {
                    MessageTime(
                        releaseDate = releaseDate,
                        isYouMessage = isYouMessage,
                    )
                }
            }
        }
    }

}

@Composable
private fun MessageTime(
    releaseDate: String,
    isYouMessage: Boolean,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .padding(top = SmallSpacing)
            .fillMaxWidth(),
    ) {
        Text(
            modifier = Modifier.align(
                if (isYouMessage) Alignment.TopEnd
                else Alignment.CenterStart
            ),
            text = releaseDate,
            style = FriendSyncTheme.typography.bodySmall.regular,
            color = FriendSyncTheme.colors.textSecondary
        )
    }
}

@Composable
private fun MessageHeader(
    releaseDate: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(LargeSpacing))
        Text(
            text = releaseDate,
            style = FriendSyncTheme.typography.bodyLarge.medium,
            color = FriendSyncTheme.colors.textSecondary
        )
        Spacer(modifier = Modifier.height(LargeSpacing))
    }
}

@Composable
private fun UserMessage(
    message: String,
    userAvatar: String,
    modifier: Modifier = Modifier,
) {
    val messageRoundedSize = 16.dp
    Box(
        modifier = modifier
            .padding(top = LargeSpacing)
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        Row(
            modifier = Modifier.align(Alignment.CenterStart),
            verticalAlignment = Alignment.Bottom
        ) {
            CircularImage(
                imageUrl = userAvatar,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(SmallSpacing))
            Box(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            topStart = 20.dp,
                            topEnd = messageRoundedSize,
                            bottomStart = 1.dp,
                            bottomEnd = messageRoundedSize
                        )
                    )
                    .background(FriendSyncTheme.colors.backgroundSecondary),

                ) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = MediumSpacing)
                        .padding(vertical = LargeSpacing),
                    text = message,
                    style = FriendSyncTheme.typography.bodyMedium.medium,
                    color = FriendSyncTheme.colors.textPrimary,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}


@Composable
private fun YourMessage(
    message: String,
    modifier: Modifier = Modifier,
) {
    val messageRoundedSize = 16.dp
    Box(
        modifier = modifier
            .padding(top = LargeSpacing)
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .clip(
                    RoundedCornerShape(
                        topStart = messageRoundedSize,
                        topEnd = 1.dp,
                        bottomStart = messageRoundedSize,
                        bottomEnd = 20.dp
                    )
                )
                .background(LargeBlue)

        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = MediumSpacing)
                    .padding(vertical = LargeSpacing),
                text = message,
                style = FriendSyncTheme.typography.bodyMedium.medium,
                color = Color.White,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
