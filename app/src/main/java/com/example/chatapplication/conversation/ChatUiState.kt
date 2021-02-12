package com.example.chatapplication.conversation

import androidx.compose.runtime.Immutable

class ChatUiState(
    val channelName : String,
    val channelMembers : Int,
    initialMessages : List<Message>
) {
    private val _messages : MutableList<Message> = mutableListOf(*initialMessages.toTypedArray())
    val messages : List<Message> = _messages

    fun addMessage(msg : Message) {
        _messages.add(msg)
    }
}

@Immutable
data class Message(
    val sender : String,
    val content : String,
    val timestamp : String,
    val image : Int? = null
)