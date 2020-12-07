package com.sas.hackathonsocs.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sas.hackathonsocs.ContactActivity
import com.sas.hackathonsocs.R
import com.sas.hackathonsocs.model.Transaction
import com.sas.hackathonsocs.model.User
import kotlinx.android.synthetic.main.recyclerview_contact.view.*
import kotlinx.android.synthetic.main.transaction_done_layout.view.*
import java.util.*
import kotlin.collections.ArrayList

class DoneTransactionAdapter (private val transactions: ArrayList<Transaction>): RecyclerView.Adapter<DoneTransactionAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var desc  = itemView.done_description
        var nominal = itemView.done_nominal
        var date = itemView.done_date
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoneTransactionAdapter.ViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.transaction_done_layout, parent, false)

        return DoneTransactionAdapter.ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DoneTransactionAdapter.ViewHolder, position: Int) {
        var curr = transactions[position]
        holder.desc.text = curr.description
        val year = Date(curr.transactionDate).year + 1900
        val month = Date(curr.transactionDate).month + 1
        val day = Date(curr.transactionDate).date
        var d = ""
        if (day < 10)d = "0"
        d+=day
        holder.date.text = "${d}/${month}/${year}"
        holder.nominal.text = curr.nominal.toString()

    }

    override fun getItemCount(): Int = transactions.size



}