package com.talk2us_Counsellor.ui.chat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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
        FirebaseDatabase.getInstance().getReference("counsellorChats")
            .child(Utils.getCounsellorId()).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                FirebaseDatabase.getInstance().getReference("Counsellor")
                    .child(Utils.getCounsellorId()).child("clients").setValue(p0.childrenCount)
            }

        })
        supportFragmentManager.beginTransaction().add(R.id.container, ChatFragment.newInstance(messageId)).commit()
    }
}
