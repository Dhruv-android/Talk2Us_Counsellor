package com.talk2us_Counsellor.ui.status

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.talk2us_Counsellor.R
import com.talk2us_Counsellor.models.Counsellor
import com.talk2us_Counsellor.ui.clientList.ClientList
import com.talk2us_Counsellor.utils.PrefManager
import com.talk2us_Counsellor.utils.Utils


class StatusActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status)
        if (PrefManager.getBoolean(R.string.status_confirmed, false)) {
            startActivity(Intent(this, ClientList::class.java))
            finish()
        } else {
            Utils.toast(PrefManager.getString(R.string.counsellor_id, "Not_available") as String)
            FirebaseDatabase.getInstance().getReference("Counsellor")
                .child(PrefManager.getString(R.string.counsellor_id, "Not_available") as String)
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
