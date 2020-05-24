package com.talk2us_Counsellor.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.PhoneAuthProvider
import com.talk2us_Counsellor.R

class ConfirmOtpFragment : Fragment() {
    private lateinit var viewModel: LoginViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_confirm_otp, container, false)
        val button = v.findViewById<Button>(R.id.confirm)
        button.setOnClickListener {
            viewModel._credential.postValue(
                PhoneAuthProvider.getCredential(
                    viewModel.mVerificationID,
                    v.findViewById<EditText>(R.id.OTP).text.toString()
                )
            )
        }
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(requireActivity()).get(LoginViewModel::class.java)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ConfirmOtpFragment().apply {
            }
    }
}
