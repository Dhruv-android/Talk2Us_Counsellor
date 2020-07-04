package com.talk2us_Counsellor.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.database.FirebaseDatabase
import com.talk2us_Counsellor.R
import com.talk2us_Counsellor.models.Counsellor
import com.talk2us_Counsellor.ui.clientList.ClientList
import com.talk2us_Counsellor.ui.status.StatusActivity
import com.talk2us_Counsellor.utils.Constants
import com.talk2us_Counsellor.utils.PrefManager
import com.talk2us_Counsellor.utils.Utils


class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()
        if (mAuth.currentUser != null) {
            startActivity(Intent(applicationContext, ClientList::class.java))
            finish()
        }
        if (PrefManager.getBoolean(Constants.FIRST_TIME, true)) {
            startActivity(Intent(applicationContext, WelcomeActivity::class.java))
            finish()
        }

        loginViewModel = ViewModelProviders.of(this)
            .get(LoginViewModel::class.java)
        loginViewModel.codeSent.observe(this, Observer {
            if (it) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, ConfirmOtpFragment()).commit()
            }
        })
        loginViewModel.credential.observe(this, Observer {
            signInWithPhoneCredential(it)
        })

        supportFragmentManager.beginTransaction().replace(R.id.container, SendOtpFragment())
            .commit()
    }

    private fun signInWithPhoneCredential(it: PhoneAuthCredential) {
        mAuth.signInWithCredential(it).addOnCompleteListener {
            if (it.isSuccessful) {
                val counsellor=Counsellor()
                counsellor.id=mAuth.currentUser?.uid as String
                FirebaseDatabase.getInstance().getReference(Constants.COUNSELLOR).child(counsellor.id).setValue(counsellor)
                PrefManager.putString(Constants.PHONE_NUMBER, loginViewModel.phone)
                PrefManager.putString(Constants.COUNSELLOR_ID,counsellor.id as String)
                startActivity(Intent(applicationContext, LoginActivity::class.java))
                finish()
            } else {
                Utils.toast(it.exception.toString())
                if (it.exception is FirebaseAuthInvalidCredentialsException) {
                    Utils.toast("Invalid OTP")
                }
            }
        }
    }
}
