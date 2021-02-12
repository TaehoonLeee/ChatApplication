package com.example.chatapplication.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chatapplication.R
import dev.chrisbanes.accompanist.insets.statusBarsHeight

@Composable
fun ColumnScope.ChatDrawer(onProfileClicked : (String) -> Unit, onChatClicked : (String) -> Unit) {
    Spacer(modifier = Modifier.statusBarsHeight())
    DrawerHeader()
    Divider()
    DrawerItemHeader("Chats")

    ChatItem("composers", true) { onChatClicked("composers") }
    ChatItem("droidcon-nyc", false) { onChatClicked("droidcon-nyc") }
    DrawerItemHeader(text = "Recent Profiles")
}

@Composable
fun ChatItem(text: String, selected: Boolean, onChatClicked: () -> Unit) {
    val background = if (selected) {
        Modifier.background(MaterialTheme.colors.primary.copy(alpha = 0.08f))
    } else { Modifier }

    Row(
        modifier = Modifier
            .preferredHeight(48.dp)
            .fillMaxHeight()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .then(background)
            .clip(MaterialTheme.shapes.medium)
            .clickable(onClick = onChatClicked),
        verticalAlignment = CenterVertically
    ) {
        val iconTint = if (selected) {
            MaterialTheme.colors.primary
        }
        else {
            MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium)
        }

        Icon(
            painterResource(id = R.drawable.chat),
            "icon",
            tint = iconTint,
            modifier = Modifier.padding(8.dp)
        )

        Providers(AmbientContentAlpha provides ContentAlpha.medium) {
            Text(
                text = text,
                style = MaterialTheme.typography.body2,
                color = if (selected) MaterialTheme.colors.primary else AmbientContentColor.current,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun DrawerItemHeader(text: String) {
    Providers(AmbientContentAlpha provides ContentAlpha.medium) {
        Text(text = text, style = MaterialTheme.typography.caption, modifier = Modifier.padding(16.dp))
    }
}

@Composable
fun DrawerHeader() {
    Row(modifier = Modifier.padding(16.dp), verticalAlignment = CenterVertically) {
        Image(
            painterResource(id = R.drawable.chat),
            "logo",
            modifier = Modifier.preferredSize(24.dp)
        )
        Image(
            painterResource(id = R.drawable.logo),
            "brand",
            modifier = Modifier.padding(8.dp)
        )
    }
}
