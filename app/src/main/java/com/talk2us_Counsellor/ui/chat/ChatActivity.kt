package com.talk2us_Counsellor.ui.chat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.iid.FirebaseInstanceId
import com.talk2us_Counsellor.R
import com.talk2us_Counsellor.models.Session
import com.talk2us_Counsellor.utils.Constants
import com.talk2us_Counsellor.utils.PrefManager
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
        FirebaseDatabase.getInstance().getReference("session").child(messageId).addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(p0: DataSnapshot) {
                val session=p0.getValue(Session::class.java)
                PrefManager.putClientMessageToken(session!!.clientToken)
            }

        })
        supportFragmentManager.beginTransaction().add(R.id.container, ChatFragment.newInstance(messageId)).commit()
    }
}
