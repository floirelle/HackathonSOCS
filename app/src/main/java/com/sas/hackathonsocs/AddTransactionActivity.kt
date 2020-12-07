package com.sas.hackathonsocs

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.FirebaseFirestore
import com.sas.hackathonsocs.model.User
import kotlinx.android.synthetic.main.activity_add_transaction.*
import java.util.*

class AddTransactionActivity : AppCompatActivity() {

    private lateinit var db :FirebaseFirestore
    private lateinit var user: User
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
//                val transaction = Tran
//                db.collection("users").document(user.email).collection("transactions").add()
            }
        }

    }
}