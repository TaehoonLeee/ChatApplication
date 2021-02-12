package com.example.chatapplication.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import com.example.chatapplication.ui.theme.ChatApplicationTheme

@Composable
fun ChatScaffOld(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    onProfileClicked : (String) -> Unit,
    onChatClicked : (String) -> Unit,
    content : @Composable (PaddingValues) -> Unit
) {
    ChatApplicationTheme {
        Scaffold(
            scaffoldState = scaffoldState,
            drawerContent = {
                ChatDrawer(
                    onProfileClicked,
                    onChatClicked
                )
            },
            bodyContent = content
        )
    }
}