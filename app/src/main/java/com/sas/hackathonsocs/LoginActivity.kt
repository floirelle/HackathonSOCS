package com.sas.hackathonsocs

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        phone_number_field.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        login_btn.setOnClickListener()
        {
            val intent = Intent(this,OtpActivity::class.java)
            intent.putExtra("phoneLogin",phone_number_field.text)
            startActivity(intent)
        }
    }
}