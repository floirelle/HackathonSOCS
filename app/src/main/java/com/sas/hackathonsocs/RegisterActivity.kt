package com.sas.hackathonsocs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import com.sas.hackathonsocs.model.User
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        register_btn.isEnabled = false
        val intent = getIntent()
        radio_gender_group.setOnCheckedChangeListener({ group, _ ->
            run {
                if (phone_number_field.text.length >= 10 && phone_number_field.text.length <= 12 )register_btn.isEnabled = true
            }
        })
        val tw = object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(c: CharSequence?, start: Int, before: Int, count: Int) {
                if(phone_number_field.text.length >= 10 && phone_number_field.text.length <= 12 && (radio_male.isChecked || radio_female.isChecked)) register_btn.isEnabled = true
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        }
        phone_number_field.addTextChangedListener(tw)

        if(intent.extras != null)
        {
            if (intent.extras!!.get("userData") != null)
            {
                val user = intent.extras!!.get("userData") as User
                full_name_field.setText(user.name, TextView.BufferType.EDITABLE)
                email_field.setText(user.email, TextView.BufferType.EDITABLE)
                full_name_field.isEnabled = false
                email_field.isEnabled = false
            }

        }
        register_btn.setOnClickListener()
        {
            val intent = Intent(this,OtpActivity::class.java)
            var gender = "Male"
            if (radio_female.isChecked) gender = "Female"
            val user = User(full_name_field.text.toString(),phone_number_field.text.toString(),email_field.text.toString(),gender)
            intent.putExtra("phoneRegister",user)
            startActivity(intent)
        }

    }
}