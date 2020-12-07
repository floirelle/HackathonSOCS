package com.sas.hackathonsocs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.sas.Firestore
import com.sas.hackathonsocs.model.User
import kotlinx.android.synthetic.main.activity_add_contact.*

class AddContactActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)
        val email = getSharedPreferences("user", MODE_PRIVATE).getString("email","").toString()

        back_arrow_btn.setOnClickListener({
            val intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)
            this.finishAffinity()
        })


        add_contact_btn.setOnClickListener(View.OnClickListener {
            var user: User = User()
            user.name = tv_name.text.toString()
            user.email = tv_email.text.toString()
            user.phoneNumber = tv_phone.text.toString()

            Firestore.instance.collection("users")
                .document(email)
                .collection("contact")
                .document(tv_email.text.toString())
                .set(user).addOnSuccessListener {
                Toast.makeText(this,"Kontak berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                val intent = Intent(this,HomeActivity::class.java)
                startActivity(intent)
                this.finishAffinity()
            }.addOnFailureListener()
            {
                Toast.makeText(this,""+it.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}