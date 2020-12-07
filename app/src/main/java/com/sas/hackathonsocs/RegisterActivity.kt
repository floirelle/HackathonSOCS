package com.sas.hackathonsocs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sas.hackathonsocs.model.User
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        register_btn.setOnClickListener()
        {
            val intent = Intent(this,OtpActivity::class.java)
            val user = User(full_name_field.text.toString(),phone_number_field.text.toString(),email_field.text.toString(), "Male")
            intent.putExtra("phoneRegister",user)
            startActivity(intent)
        }

    }
}