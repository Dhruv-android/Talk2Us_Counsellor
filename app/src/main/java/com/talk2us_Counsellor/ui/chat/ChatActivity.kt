package com.talk2us_Counsellor.ui.chat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.talk2us_Counsellor.R
import com.talk2us_Counsellor.utils.Utils

class ChatActivity : AppCompatActivity() {
    lateinit var messageId:String
    lateinit var viewModel: ChatViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        viewModel = ViewModelProviders.of(this).get(ChatViewModel::class.java)
        val str = intent.getStringExtra("message_id")
        viewModel.message_id = str as String
        messageId=str as String
        supportFragmentManager.beginTransaction().add(R.id.container, ChatFragment.newInstance(messageId)).commit()
    }
}
