package com.sas.hackathonsocs.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.sas.Firestore
import com.sas.hackathonsocs.AddContactActivity
import com.sas.hackathonsocs.R
import com.sas.hackathonsocs.adapter.ContactAdapter
import com.sas.hackathonsocs.model.Transaction
import com.sas.hackathonsocs.model.User
import kotlinx.android.synthetic.main.fragment_contact.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.ArrayList

class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val email = this.activity!!
            .getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)
            .getString("email", "").toString()
        tv_name.text = this.activity!!
            .getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)
            .getString("name", "").toString()
        Firestore.instance.collection("users")
            .document(email)
            .collection("transactions")
            .addSnapshotListener{ value, e ->
                if(e != null){
//                    no_contact.visibility = View.VISIBLE
                    tv_summary_pendapatan.text = "IDR 0"
                    tv_summary_pengeluaran.text = "IDR 0"
                    return@addSnapshotListener}
                var income = 0
                var expense = 0
                for (doc in value!!) {
                   val transaction = doc.toObject(Transaction::class.java)!!
                    if(transaction.type.equals("Income")) income += transaction.nominal.toInt()
                    else expense += transaction.nominal.toInt()
                }
                tv_summary_pendapatan.text = "IDR "+income.toInt()
                tv_summary_pengeluaran.text = "IDR "+expense.toInt()
            }
    }
}