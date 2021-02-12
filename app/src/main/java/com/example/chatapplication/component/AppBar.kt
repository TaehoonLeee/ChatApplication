package com.example.chatapplication.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.chatapplication.R
import com.example.chatapplication.ui.theme.elevatedSurface

@Composable
fun ChatAppBar(
    modifier: Modifier = Modifier,
    onNavIconPressed: () -> Unit = { },
    title: @Composable RowScope.() -> Unit,
    actions: @Composable RowScope.() -> Unit = {}
) {
    Column {
        // This bar needs to be translucent but, if the backgroundColor in TopAppBar is not
        // opaque, the elevation is ignored. We need to manually calculate the elevated surface
        // color for dark mode:
        val backgroundColor = MaterialTheme.colors.elevatedSurface(3.dp)
        TopAppBar(
            modifier = modifier,
            backgroundColor = backgroundColor.copy(alpha = 0.95f),
            elevation = 0.dp, // No shadow needed
            contentColor = MaterialTheme.colors.onSurface,
            actions = actions,
            title = { Row { title() } }, // https://issuetracker.google.com/168793068
            navigationIcon = {
                Image(
                    painter = painterResource(id = R.drawable.chat),
                    contentDescription = "navigate",
                    modifier = Modifier
                        .clickable(onClick = onNavIconPressed)
                        .padding(horizontal = 16.dp)
                )
            }
        )
        Divider()
    }
}