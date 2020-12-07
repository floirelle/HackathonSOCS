package com.sas.hackathonsocs.adapter

import com.sas.hackathonsocs.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import com.sas.hackathonsocs.model.Transaction
import kotlinx.android.synthetic.main.transaction_due_layout.view.*
import java.util.*


class DueTransactionAdapter(var transactions : ArrayList<Transaction>) : PagerAdapter() {

    // Context object
    var context: Context? = null

    // Array of images


    // Layout Inflater
    var mLayoutInflater: LayoutInflater? = null


    // Viewpager Constructor
    fun ViewPagerAdapter(context: Context, transaction:ArrayList<Transaction>) {
        this.context = context
        transactions = transaction
        mLayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getCount(): Int {
        // return the number of images
        return transactions.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as LinearLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        // inflating the item.xml
        val itemView: View = mLayoutInflater!!.inflate(R.layout.transaction_due_layout, container, false)

        val curr = transactions[position]
        // referencing the image view from the item.xml file
        val desc = curr.description
        val nominal = curr.nominal

        val year = Date(curr.transactionDate).year
        val month = Date(curr.transactionDate).month + 1
        val day = Date(curr.transactionDate).day
        val date= "${day}/${month}/${year}"
        var msg = "Lunas"
        if (curr.type !="Expense") msg = "Bayar"
        itemView.due_nominal.text = nominal.toString()
        itemView.due_date.text = date
        itemView.due_btn.text =  msg
        itemView.due_desc.text = desc

        // setting the image in the imageView


        // Adding the View
        Objects.requireNonNull(container).addView(itemView)
        return itemView
    }


}