package com.example.chatapplication.conversation

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chatapplication.R
import com.example.chatapplication.component.ChatAppBar
import com.example.chatapplication.ui.theme.ChatApplicationTheme
import com.example.chatapplication.ui.theme.elevatedSurface
import com.example.chatapplication.utils.exampleUiState
import dev.chrisbanes.accompanist.insets.navigationBarsPadding
import dev.chrisbanes.accompanist.insets.navigationBarsWithImePadding
import dev.chrisbanes.accompanist.insets.statusBarsPadding
import kotlinx.coroutines.launch

@Composable
fun ChatContent(
    uiState : ChatUiState,
    navigateToProfile: (String) -> Unit,
    modifier : Modifier = Modifier,
    onNavIconPressed : () -> Unit = {}
) {
    val senderMe = "me"
    val timeNow = "8:30 PM"

    val scrollState = rememberScrollState()
    Surface(modifier = modifier) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                Messages(
                    messages = uiState.messages,
                    navigateToProfile = navigateToProfile,
                    modifier = Modifier.weight(1f),
                    scrollState = scrollState
                )
                UserInput(
                    onMessageSent = { message ->
                        uiState.addMessage(Message(senderMe, message, timeNow))
                    },
                    scrollState = scrollState,
                    modifier = Modifier.navigationBarsWithImePadding()
                )
            }

            ChannelNumberBar(
                channelName = uiState.channelName,
                channelMembers = uiState.channelMembers,
                onNavIconPressed = onNavIconPressed,
                modifier = Modifier.statusBarsPadding()
            )
        }
//
    }
}

@Composable
fun ChannelNumberBar(
    channelName : String,
    channelMembers : Int,
    modifier : Modifier = Modifier,
    onNavIconPressed: () -> Unit = { }
) {
    ChatAppBar(
        modifier = modifier,
        onNavIconPressed = onNavIconPressed,
        title = {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = channelName,
                    style = MaterialTheme.typography.subtitle1
                )
                Providers(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(
                        text = "$channelMembers members",
                        style = MaterialTheme.typography.caption
                    )
                }
            }
        },
        actions = {
            Providers(LocalContentAlpha provides ContentAlpha.medium) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    modifier = Modifier
                        .clickable(onClick = { }) // TODO
                        .padding(horizontal = 12.dp, vertical = 16.dp)
                        .preferredHeight(24.dp),
                    contentDescription = "Search"
                )

                Icon(
                    imageVector = Icons.Outlined.Info,
                    modifier = Modifier
                        .clickable(onClick = { }) // TODO
                        .padding(horizontal = 12.dp, vertical = 16.dp)
                        .preferredHeight(24.dp),
                    contentDescription = "Info"
                )
            }
        }
    )
}

@Composable
fun Messages(
    messages : List<Message>,
    navigateToProfile : (String) -> Unit,
    scrollState : ScrollState,
    modifier : Modifier
) {
    val scope = rememberCoroutineScope()
    Box(modifier = modifier) {
        Column(
            Modifier
                .testTag("ChatTestTag")
                .fillMaxWidth()
                .verticalScroll(scrollState, reverseScrolling = true)
        ) {
            val senderMe = "me"
            Spacer(modifier = Modifier.preferredHeight(64.dp))
            messages.forEachIndexed { index, message ->
                val prevSender = messages.getOrNull(index-1)?.sender
                val nextSender = messages.getOrNull(index+1)?.sender
                val isFirstMessageBySender = prevSender != message.sender
                val isLastMessageBySender = nextSender != message.sender

                if ( index == 0 ) {
                    DayHeader("20 Aug")
                }
                else if ( index == 4 ){
                    DayHeader(dayString = "Today")
                }

                Message(
                    onSenderClick = {
                        navigateToProfile(message.sender)
                    },
                    message = message,
                    isUserMe = message.sender == senderMe,
                    isFirstMessageBySender = isFirstMessageBySender,
                    isLastMessageBySender = isLastMessageBySender
                )
            }
        }

        val jumpToBottomThreshold = 56.dp
        val jumpThreshold = with(LocalDensity.current) {
            jumpToBottomThreshold.toPx()
        }

        val jumpToBottomButtonEnabled = scrollState.value > jumpThreshold

        JumpToBottom(
            enabled = jumpToBottomButtonEnabled,
            onClicked = {
                scope.launch {
                    scrollState.smoothScrollTo(0f)
                }
            },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun Message(
    onSenderClick : () -> Unit,
    message : Message,
    isUserMe : Boolean,
    isFirstMessageBySender : Boolean,
    isLastMessageBySender : Boolean
) {
    val painter = if (isUserMe) {
        painterResource(id = R.drawable.taehoon)
    }
    else {
        painterResource(id = R.drawable.dwayne)
    }

    val borderColor = if (isUserMe) {
        MaterialTheme.colors.primary
    }
    else {
        MaterialTheme.colors.secondary
    }

    val spaceBetweenSenders = if (isFirstMessageBySender) Modifier.padding(8.dp) else Modifier
    Row(modifier = spaceBetweenSenders) {
        if (isFirstMessageBySender) {
            Image(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .preferredSize(42.dp)
                    .border(1.5.dp, borderColor, CircleShape)
                    .border(3.dp, MaterialTheme.colors.surface, CircleShape)
                    .clip(CircleShape)
                    .clickable(onClick = onSenderClick)
                    .align(Alignment.Top),
                painter = painter,
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
        }
        else {
            Spacer(modifier = Modifier.preferredWidth(74.dp))
        }


//
    }
}

@Composable
fun SenderAndTextMessage(
    msg: Message,
    isFirstMessageBySender: Boolean,
    isLastMessageBySender: Boolean,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        if (isFirstMessageBySender) {
            SenderNameTimestamp(msg)
        }
        ChatItemBubble(msg, isLastMessageBySender)
        if (isLastMessageBySender) {
            // Last bubble before next author
            Spacer(modifier = Modifier.preferredHeight(8.dp))
        } else {
            // Between bubbles
            Spacer(modifier = Modifier.preferredHeight(4.dp))
        }
    }
}

@Composable
private fun SenderNameTimestamp(msg: Message) {
    // Combine author and timestamp for a11y.
    Row(modifier = Modifier.semantics(mergeDescendants = true) {}) {
        Text(
            text = msg.sender,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier
                .alignBy(LastBaseline)
                .paddingFrom(LastBaseline, after = 8.dp) // Space to 1st bubble
        )
        Spacer(modifier = Modifier.preferredWidth(8.dp))
        Providers(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = msg.timestamp,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.alignBy(LastBaseline)
            )
        }
    }
}

private val ChatBubbleShape = RoundedCornerShape(0.dp, 8.dp, 8.dp, 0.dp)
private val LastChatBubbleShape = RoundedCornerShape(0.dp, 8.dp, 8.dp, 8.dp)
@Composable
fun ChatItemBubble(
    message: Message,
    lastMessageByAuthor: Boolean
) {

    val backgroundBubbleColor =
        if (MaterialTheme.colors.isLight) {
            Color(0xFFF5F5F5)
        } else {
            MaterialTheme.colors.elevatedSurface(2.dp)
        }

    val bubbleShape = if (lastMessageByAuthor) LastChatBubbleShape else ChatBubbleShape
    Column {
        Surface(color = backgroundBubbleColor, shape = bubbleShape) {
            ClickableMessage(
                message = message
            )
        }

        message.image?.let {
            Spacer(modifier = Modifier.height(4.dp))
            Surface(color = backgroundBubbleColor, shape = bubbleShape) {
                Image(
                    painter = painterResource(it),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.preferredSize(160.dp),
                    contentDescription = "Attached Image"
                )
            }
        }
    }
}

@Composable
fun ClickableMessage(message: Message) {
    val uriHandler = LocalUriHandler.current

    val styledMessage = messageFormatter(text = message.content)

    ClickableText(
        text = styledMessage,
        style = MaterialTheme.typography.body1.copy(color = LocalContentColor.current),
        modifier = Modifier.padding(8.dp),
        onClick = {
            styledMessage
                .getStringAnnotations(start = it, end = it)
                .firstOrNull()
                ?.let { annotation ->
                    when (annotation.tag) {
                        SymbolAnnotationType.LINK.name -> uriHandler.openUri(annotation.item)
                        // TODO(yrezgui): Open profile screen when click PERSON tag
                        //  (e.g. @aliconors)
                        else -> Unit
                    }
                }
        }
    )
}

@Composable
fun DayHeader(dayString: String) {
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .preferredHeight(16.dp)
    ) {
        DayHeaderLine()
        Providers(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = dayString,
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.overline
            )
        }
        DayHeaderLine()
    }
}

@Composable
private fun RowScope.DayHeaderLine() {
    Divider(
        modifier = Modifier
            .weight(1f)
            .align(Alignment.CenterVertically),
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
    )
}