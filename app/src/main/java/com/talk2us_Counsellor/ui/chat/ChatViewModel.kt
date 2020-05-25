package com.talk2us_Counsellor.ui.chat

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.talk2us_Counsellor.models.Message
import com.talk2us_Counsellor.ui.chat.datasource.ChatDatabase
import com.talk2us_Counsellor.ui.chat.datasource.ChatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatViewModel(application:Application) : AndroidViewModel(application) {
    private val repository: ChatRepository
    var message_id=""
    var progress= MutableLiveData<Boolean>()
    init {
        val wordsDao = ChatDatabase.getDatabase(application, viewModelScope).wordDao()
        repository = ChatRepository(wordsDao, this)
        progress.postValue(false)
    }

    fun getAllMessages(str:String):LiveData<List<Message>>{
        return repository.getAllMessages(str)
    }
    fun sendMessage(message: Message) = viewModelScope.launch(Dispatchers.IO) {
        repository.sendMessage(message)
    }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }

    fun update(message: Message) = viewModelScope.launch(Dispatchers.IO) {
        message.sent = true
        repository.update(message)
    }

    fun insertLocally(message:Message)=viewModelScope.launch(Dispatchers.IO){
        repository.insertLocally(message)
    }}
