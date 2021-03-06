package com.example.chatapplication.utils

import com.example.chatapplication.R
import com.example.chatapplication.conversation.ChatUiState
import com.example.chatapplication.conversation.Message

private val initialMessages = listOf(
    Message(
        "me",
        "Compose newbie: I’ve scourged the internet for tutorials about async data loading " +
                "but haven’t found any good ones. What’s the recommended way to load async " +
                "data and emit composable widgets?",
        "8:03 PM"
    ),
    Message(
        "John Glenn",
        "Compose newbie as well, have you looked at the JetNews sample? Most blog posts end up " +
                "out of date pretty fast but this sample is always up to date and deals with async " +
                "data loading (it's faked but the same idea applies) \uD83D\uDC49" +
                "https://github.com/android/compose-samples/tree/master/JetNews",
        "8:04 PM"
    ),
    Message(
        "Taylor Brooks",
        "@aliconors Take a look at the `Flow.collectAsState()` APIs",
        "8:05 PM"
    ),
    Message(
        "Taylor Brooks",
        "You can use all the same stuff",
        "8:05 PM"
    ),
    Message(
        "me",
        "Thank you!",
        "8:06 PM",
        R.drawable.happy
    ),
    Message(
        "me",
        "Check it out!",
        "8:07 PM"
    )
)

val exampleUiState = ChatUiState(
    initialMessages = initialMessages,
    channelName = "#composers",
    channelMembers = 42
)