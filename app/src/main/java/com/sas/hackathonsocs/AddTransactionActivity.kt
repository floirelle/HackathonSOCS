package com.sas.hackathonsocs

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.FirebaseFirestore
import com.sas.hackathonsocs.model.Transaction
import com.sas.hackathonsocs.model.User
import kotlinx.android.synthetic.main.activity_add_transaction.*
import java.util.*

class AddTransactionActivity : AppCompatActivity() {

    private lateinit var db :FirebaseFirestore
    private lateinit var user: User
    private var datePicked = 0L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_transaction)
        db = FirebaseFirestore.getInstance()
        val intent = getIntent()
        user = intent.extras!!.get("user") as User
        date_transaction_field.setOnClickListener()
        {
            val currentDate = Date()
            val datePicker = DatePickerDialog(this)
            datePicker.setOnDateSetListener(){
                    view,year,month,day ->
                run {

                    val calendar = Calendar.getInstance()
                    calendar.set(Calendar.YEAR,year)
                    calendar.set(Calendar.MONTH,month)
                    calendar.set(Calendar.DAY_OF_MONTH,day)
                    datePicked = calendar.time.time


                    var d = ""
                    if (day < 10)d = "0"
                    d+=day
                    date_transaction_field.text = "$d/${month+1}/$year"
                }
            }
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.DAY_OF_MONTH,1)
            datePicker.datePicker.minDate = calendar.time.time
            datePicker.datePicker.maxDate = Date().time
            datePicker.show()
        }

        add_transaction_btn.setOnClickListener()
        {
            if(date_transaction_field.text != "Pilih Tanggal" && transaction_description_field.text.toString() != ""
                && (radio_expenses.isChecked || radio_income.isChecked) && (radio_paid.isChecked || radio_due.isChecked)
                && transaction_nominal.length() != 0 && (radio_weekly.isChecked || radio_monthly.isChecked || radio_never.isChecked))
            {
                val desc = transaction_description_field.text.toString()
                var type = "Expense"
                if(radio_income.isChecked)type = "Income"
                var status = "Paid"
                if(!radio_paid.isChecked) status = "Due"
                val nominal = transaction_nominal.text.toString().toLong()
                var reminder = "Weekly"
                if(radio_monthly.isChecked) reminder = "Monthly"
                if(radio_never.isChecked) reminder = "Never"
                val transaction = Transaction(datePicked,desc,type,status,nominal,reminder)
                db.collection("users").document(user.email).collection("transactions").add(transaction).addOnCompleteListener()
                {
                    val intent = Intent(this,HomeActivity::class.java)
                    intent.putExtra("user",user)
                    startActivity(intent)
                }
            }
        }

    }
}