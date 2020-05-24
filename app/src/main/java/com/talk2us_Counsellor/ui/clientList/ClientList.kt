package com.talk2us_Counsellor.ui.clientList

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.talk2us_Counsellor.R
import com.talk2us_Counsellor.ui.chat.ChatActivity
import com.talk2us_Counsellor.utils.PrefManager

class ClientList : AppCompatActivity(),
    onClick {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_list)
        val recyclerView=findViewById<RecyclerView>(R.id.rv_client_id)
        val adapter= ClientAdapter(this)
        recyclerView.adapter=adapter
        recyclerView.layoutManager= LinearLayoutManager(this)
        adapter.setOnClick(this)

        FirebaseDatabase.getInstance().getReference("counsellorChats").child(
            PrefManager.getString(R.string.counsellor_id, "Not available") as String
        ).addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot) {
                for(children in p0.children){
                    val str=children.key
                    adapter.updateList(str as String)
                }
            }

        })

    }

    override fun onClick(str: String) {
        val intent=Intent(this, ChatActivity::class.java)
        intent.putExtra("message_id",str)
        startActivity(intent)
    }
}
