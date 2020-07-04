package com.talk2us_Counsellor.ui.clientList

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.talk2us_Counsellor.R
import com.talk2us_Counsellor.ui.chat.ChatActivity
import com.talk2us_Counsellor.ui.status.StatusActivity
import com.talk2us_Counsellor.utils.Constants
import com.talk2us_Counsellor.utils.FirebaseUtils
import com.talk2us_Counsellor.utils.PrefManager

class ClientList : AppCompatActivity(),
    onClick, FirebaseUtils.ChangeListener {
    lateinit var viewModel: ClientListViewModel
    lateinit var firebaseUtils: FirebaseUtils
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_list)
        val recyclerView=findViewById<RecyclerView>(R.id.rv_client_id)
        val adapter= ClientAdapter(this)
        viewModel = ViewModelProviders.of(this).get(ClientListViewModel::class.java)
        recyclerView.adapter=adapter
        recyclerView.layoutManager= LinearLayoutManager(this)
        adapter.setOnClick(this)
        firebaseUtils = FirebaseUtils.getInstance()
        firebaseUtils.setListeners(this)


        FirebaseDatabase.getInstance().getReference("counsellorChats").child(
            PrefManager.getString(Constants.COUNSELLOR_ID, Constants.NOT_DEFINED) as String
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

    override fun onStatusChange(boolean: Boolean) {
        PrefManager.putBoolean(Constants.STATUS,boolean)
        if(!boolean){
            val intent=Intent(this,StatusActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    }

    override fun onClick(str: String) {
        val intent=Intent(this, ChatActivity::class.java)
        intent.putExtra("message_id",str)
        startActivity(intent)
    }
}
