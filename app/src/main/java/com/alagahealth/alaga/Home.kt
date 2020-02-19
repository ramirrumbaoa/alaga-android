package com.alagahealth.alaga

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_home.view.*

class Home : Fragment(){

    private val home2 = Home2()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        view.homeNext.setOnClickListener(){
            Log.d("HOME FRAGMENT", "NEXT FRAGMENT")
            (activity as MainActivity).addFragment(home2)
        }
        return view
    }
}