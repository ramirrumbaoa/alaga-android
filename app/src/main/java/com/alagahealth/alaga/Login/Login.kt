package com.alagahealth.alaga.Login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alagahealth.alaga.MainActivity
import com.alagahealth.alaga.R
import kotlinx.android.synthetic.main.fragment_login.view.*

class Login : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_login, container, false)


        view.loginSubmit.setOnClickListener{
            Log.d("Test", "Login Button")
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }


        return view
    }

}