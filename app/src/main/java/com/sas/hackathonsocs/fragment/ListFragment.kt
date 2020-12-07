package com.sas.hackathonsocs.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sas.hackathonsocs.AddTransactionActivity
import com.sas.hackathonsocs.R
import com.sas.hackathonsocs.model.User
import kotlinx.android.synthetic.main.fragment_list.*


class ListFragment : Fragment() {

    private lateinit var user : User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = this.activity!!.intent
        user = intent.extras!!.get("user") as User

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        add_btn.setOnClickListener()
        {
            val intent = Intent(this.activity,AddTransactionActivity::class.java)
            intent.putExtra("user",user)
            startActivity(intent)
        }

    }
}