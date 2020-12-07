package com.sas.hackathonsocs.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import com.sas.hackathonsocs.R
import com.sas.hackathonsocs.model.Transaction
import kotlinx.android.synthetic.main.transaction_due_layout.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ofLocalizedDate
import java.time.format.FormatStyle
import java.util.*


class DueTransactionAdapter() : PagerAdapter() {

    // Context object
    var context: Context? = null

    // Array of images
    lateinit var transactions :ArrayList<Transaction>

    // Layout Inflater
    var mLayoutInflater: LayoutInflater? = null


    // Viewpager Constructor
    constructor(context: Context, transaction: ArrayList<Transaction>) : this() {
        this.context = context
        transactions = transaction
        Log.d("T", "" + transactions.size)
        mLayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getCount(): Int {
        // return the number of images
        return transactions.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {

        container.removeView(`object` as GridLayout)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as GridLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        // inflating the item.xml
        val itemView: View = mLayoutInflater!!.inflate(
            R.layout.transaction_due_layout,
            container,
            false
        )

        val curr = transactions[position]

        // referencing the image view from the item.xml file
        val desc = curr.description
        val nominal = curr.nominal
        val dates = Date(curr.transactionDate)
        val localDate = LocalDate.parse(dates.toGMTString())
        val calendar = Calendar.getInstance()
        val year = dates.year
        val month = Date(curr.transactionDate).month + 1
        val day = Date(curr.transactionDate).date
        var d = ""
        Log.d("TA",""+localDate)
        if (day < 10)d = "0"
        d+=day
        val date= "${d}/${month}/2020"
        var msg = "Lunas"
        if (curr.type !="Expense") msg = "Bayar"
        itemView.due_nominal.text = nominal.toString()
        itemView.due_date.text = date
        itemView.due_btn.text =  msg
        itemView.due_desc.text = desc

        // setting the image in the imageView

        container.addView(itemView)
        // Adding the View

        return itemView
    }


}