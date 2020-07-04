package com.talk2us_Counsellor.utils

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.iid.FirebaseInstanceId
import com.talk2us_Counsellor.models.Message

class FirebaseUtils {
    companion object {
        fun getInstance(): FirebaseUtils {
            return FirebaseUtils()
        }
    }

    fun sendMessage(message: Message, listener: Listener<Message>) {
        FirebaseDatabase.getInstance().getReference("chatMessages").child(message.messageId)
            .child(message.timeStamp).setValue(message).addOnSuccessListener {
                listener.onSuccess(message)
            }
    }

    fun manageCounsellorToken(listener: Listener<String>) {
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
            val token = it.token
            FirebaseDatabase.getInstance().getReference(Constants.COUNSELLOR)
                .child(PrefManager.getCounsellorId()).child("messageToken").setValue(token)
                .addOnSuccessListener {
                    listener.onSuccess(token)
                }
        }
    }

    fun setListeners(changeListener:ChangeListener) {
        val database = FirebaseDatabase.getInstance()
        database.getReference("counsellor").child(PrefManager.getCounsellorId())
            .child(Constants.STATUS).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                val status = p0.getValue(Boolean::class.java)
                changeListener.onStatusChange(status as Boolean)
            }
        })
    }

    interface ChangeListener {
        fun onStatusChange(boolean: Boolean);
    }

    public interface Listener<T> {
        fun onSuccess(it: T)
        fun onError(error: DatabaseError)
    }
}