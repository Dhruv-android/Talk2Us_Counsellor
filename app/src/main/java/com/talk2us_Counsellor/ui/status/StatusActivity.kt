package com.talk2us_Counsellor.ui.status

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseError
import com.talk2us_Counsellor.R
import com.talk2us_Counsellor.ui.clientList.ClientList
import com.talk2us_Counsellor.utils.Constants
import com.talk2us_Counsellor.utils.FirebaseUtils
import com.talk2us_Counsellor.utils.PrefManager


class StatusActivity : AppCompatActivity(), FirebaseUtils.ChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status)
        FirebaseUtils.getInstance().setListeners(this)
        if (PrefManager.getCounsellorMessageToken() == Constants.NOT_DEFINED) {
            FirebaseUtils.getInstance()
                .manageCounsellorToken(object : FirebaseUtils.Listener<String> {
                    override fun onSuccess(it: String) {
                    }

                    override fun onError(error: DatabaseError) {
                    }
                })
        }

    }

    override fun onStatusChange(boolean: Boolean) {
        PrefManager.putBoolean(Constants.STATUS, boolean)
        if (boolean) {
            val intent=Intent(this,ClientList::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    }
}

