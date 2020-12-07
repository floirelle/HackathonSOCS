package com.sas.hackathonsocs.fragment

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils.replace
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.firebase.firestore.FirebaseFirestore
import com.sas.hackathonsocs.AddTransactionActivity
import com.sas.hackathonsocs.R
import com.sas.hackathonsocs.adapter.DoneTransactionAdapter
import com.sas.hackathonsocs.adapter.DueTransactionAdapter
import com.sas.hackathonsocs.model.Transaction
import com.sas.hackathonsocs.model.User
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_list.nav_bottom


class ListFragment : Fragment() {


    private lateinit var recyclerView: RecyclerView
    private lateinit var viewPager : ViewPager
    private lateinit var recyclerAdapter: DoneTransactionAdapter
    private lateinit var viewAdapter : DueTransactionAdapter
    private lateinit var db: FirebaseFirestore
    private lateinit var user:User
    private lateinit var salesDoneTransactions : ArrayList<Transaction>
    private lateinit var salesDueTransactions : ArrayList<Transaction>
    private var mode = "purchase"
    private lateinit var purchaseDoneTransactions : ArrayList<Transaction>
    private lateinit var purchaseDueTransactions : ArrayList<Transaction>
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
        db = FirebaseFirestore.getInstance()



        salesDoneTransactions = ArrayList<Transaction>()
        salesDueTransactions = ArrayList<Transaction>()
        purchaseDoneTransactions = ArrayList<Transaction>()
        purchaseDueTransactions = ArrayList<Transaction>()

        val intent = this.activity!!.intent
        recyclerView = recycler_view
        recyclerView.layoutManager = LinearLayoutManager(this.activity)
        viewPager = viewPagerMain
        user = this.activity!!.getIntent().extras!!.get("user") as User
        db.collection("users").document(user.email).collection("transactions").get().addOnCompleteListener()
        {
            if(it.isSuccessful)
            {
                Log.d("TAT",""+it.result!!.size())
                val i = it.result!!.documents
                for (tr in i)
                {
                    if(tr.get("type") == "Income")
                    {
                        //income
                        if(tr.get("status") == "Paid")
                        {
                            salesDoneTransactions.add(tr.toObject(Transaction::class.java)!!)
                        }
                        else{
                            salesDueTransactions.add(tr.toObject(Transaction::class.java)!!)
                        }
                    }
                    else{
                        if(tr.get("status") == "Paid")
                        {
                            purchaseDoneTransactions.add(tr.toObject(Transaction::class.java)!!)
                        }
                        else{
                            purchaseDueTransactions.add(tr.toObject(Transaction::class.java)!!)
                        }
                        //expense
                    }

                }
                val adapter = DoneTransactionAdapter(salesDoneTransactions)
                val adapter2 = DueTransactionAdapter(salesDueTransactions)
                recyclerView.adapter = adapter
                viewPager.adapter = adapter2
                adapter.notifyDataSetChanged()
                adapter2.notifyDataSetChanged()

                when(mode){
                    "purchase" ->
                    {
                        recyclerView.adapter = DoneTransactionAdapter(purchaseDoneTransactions)
                        viewPager.adapter = DueTransactionAdapter(purchaseDueTransactions)
                    }

                    "sales" ->
                    {
                        recyclerView.adapter = DoneTransactionAdapter(salesDoneTransactions)
                        viewPager.adapter = DueTransactionAdapter(salesDueTransactions)
                    }

                }

            }
            else Log.d("TEST","UNSUCESSFUL")
        }
        add_btn.setOnClickListener()
        {
            val intent = Intent(this.activity,AddTransactionActivity::class.java)
            intent.putExtra("user",user)
            startActivity(intent)
        }


    }

}