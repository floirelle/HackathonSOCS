package com.sas.hackathonsocs.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.firebase.firestore.FirebaseFirestore
import com.sas.hackathonsocs.R
import com.sas.hackathonsocs.adapter.DoneTransactionAdapter
import com.sas.hackathonsocs.adapter.DueTransactionAdapter
import com.sas.hackathonsocs.model.Transaction
import com.sas.hackathonsocs.model.User
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_list.nav_bottom
import kotlinx.android.synthetic.main.fragment_transaction_list.*


class TransactionListFragment : Fragment() {

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

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transaction_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        }

    }
