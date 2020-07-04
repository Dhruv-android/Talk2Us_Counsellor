package com.talk2us_Counsellor.ui.chat.datasource

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.google.firebase.database.DatabaseError
import com.talk2us_Counsellor.models.Message
import com.talk2us_Counsellor.ui.chat.ChatViewModel
import com.talk2us_Counsellor.utils.FirebaseUtils


class ChatRepository(private val chatDao: ChatDao, private val viewModel: ChatViewModel) {

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun sendMessage(message: Message) {
        chatDao.sendMessage(message)
        FirebaseUtils.getInstance().sendMessage(message, object : FirebaseUtils.Listener<Message> {
            override fun onSuccess(it: Message) {
                viewModel.update(message)
            }

            override fun onError(error: DatabaseError) {
            }

        })
        }

    @WorkerThread
    fun getAllMessages(str:String):LiveData<List<Message>>{
        return chatDao.getAllMessages(str)
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