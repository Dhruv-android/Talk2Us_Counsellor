package com.talk2us_Counsellor.ui.status

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.iid.FirebaseInstanceId
import com.talk2us_Counsellor.R
import com.talk2us_Counsellor.models.Counsellor
import com.talk2us_Counsellor.ui.clientList.ClientList
import com.talk2us_Counsellor.utils.Constants
import com.talk2us_Counsellor.utils.PrefManager
import com.talk2us_Counsellor.utils.Utils


class StatusActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status)
        if (PrefManager.getString(
                Constants.COUNSELLOR_MESSAGE_TOKEN,
                Constants.NOT_DEFINED
            ) == Constants.NOT_DEFINED
        ) {
            FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener(OnCompleteListener {
                if (!it.isSuccessful) {
                    return@OnCompleteListener
                }
                val token = it.result?.token
                FirebaseDatabase.getInstance().getReference(
                    "/counsellor/" + PrefManager.getString(
                        Constants.COUNSELLOR_ID,
                        Constants.NOT_DEFINED
                    )
                ).child("messageToken").setValue(token)
                PrefManager.putCounsellorMessageToken(token as String)
            })
        }
        if (PrefManager.getBoolean(R.string.status_confirmed, false)) {
            startActivity(Intent(this, ClientList::class.java))
            finish()
        } else {
            Utils.toast(
                PrefManager.getString(
                    Constants.COUNSELLOR_ID,
                    Constants.NOT_DEFINED
                ) as String
            )
            FirebaseDatabase.getInstance().getReference(Constants.COUNSELLOR)
                .child(
                    PrefManager.getString(
                        Constants.COUNSELLOR_ID,
                        Constants.NOT_DEFINED
                    ) as String
                )
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val counsellor=dataSnapshot.getValue(Counsellor::class.java)
                        if(counsellor?.status_confirmed as Boolean){
                            PrefManager.putBoolean(R.string.status_confirmed,true)
                            startActivity(Intent(applicationContext,
                                ClientList::class.java))
                            finish()
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        Toast.makeText(
                            applicationContext,
                            databaseError.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        }
    }
}
