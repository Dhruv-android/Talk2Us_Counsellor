package com.talk2us_Counsellor.ui.chat.datasource

import android.util.Log
import android.util.Log.*
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.google.firebase.database.*
import com.talk2us_Counsellor.models.Counsellor
import com.talk2us_Counsellor.models.Message
import com.talk2us_Counsellor.ui.chat.ChatViewModel
import com.talk2us_Counsellor.utils.PrefManager
import com.talk2us_Counsellor.utils.Utils


class ChatRepository(private val chatDao: ChatDao, private val viewModel: ChatViewModel) {

    val allWords: LiveData<List<Message>> = chatDao.getAllMessages()
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun sendMessage(message: Message) {
        chatDao.sendMessage(message)
        val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("chatMessages").child(
                message.messageId
            ).child(message.timeStamp)
            myRef.setValue(message).addOnSuccessListener {
                viewModel.update(message)
            }
        }

    @WorkerThread
    fun update(message: Message) {
        chatDao.updateLast(message)
    }

    @WorkerThread
    fun insertLocally(message: Message){
        chatDao.sendMessage(message)
    }

    @WorkerThread
    fun deleteAll() {
        chatDao.deleteAll()
    }
}