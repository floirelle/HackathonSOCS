package com.sas.hackathonsocs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.webkit.URLUtil
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.sas.hackathonsocs.model.Company
import com.sas.hackathonsocs.model.CompanyProfile
import com.sas.hackathonsocs.model.User
import kotlinx.android.synthetic.main.activity_add_company_profile.*

class AddCompanyProfile : AppCompatActivity() {

    private lateinit var db:FirebaseFirestore
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_company_profile)
        db = FirebaseFirestore.getInstance()
        val intent = getIntent()
        user = intent.extras!!.get("user") as User
        save_company_profile_btn.setOnClickListener()
        {
            if(company_profile_field.text.toString() != "" && (radio_kecil.isChecked || radio_mikro.isChecked || radio_menengah.isChecked))
            {
                if((Patterns.WEB_URL.matcher(link_tokopedia.text.toString()).matches() || link_tokopedia.text.toString() == "") && ((URLUtil.isValidUrl(link_shopee.text.toString()) || link_shopee.text.toString() == "")) && ((URLUtil.isValidUrl(link_other.text.toString()) || link_other.text.toString() == "")))
                {
                    val links = HashMap<String,String>()
                    links["tokopedia"] = link_tokopedia.text.toString()
                    links["shopee"] = link_shopee.text.toString()
                    links["other"] = link_other.text.toString()
                    var type = "Mikro"
                    if(radio_kecil.isChecked)type = "Kecil"
                    else if (radio_menengah.isChecked) type = "Menengah"
                    val company = Company(company_profile_field.text.toString(), type,links)
                    db.collection("users").document(user.email).collection("company").add(company).addOnCompleteListener()
                    {
                        val intent = Intent(this,HomeActivity::class.java)
                        intent.putExtra("user",user)
                        startActivity(intent)
                    }
                }
                else {
                    Toast.makeText(this,"Mohon masukkan link yang benar, kosongkan jika tidak ada",Toast.LENGTH_SHORT).show()
                }

            }
            else{
                Toast.makeText(this,"Mohon masukkan nama usaha dan jenis usaha",Toast.LENGTH_SHORT).show()
            }

        }

    }
}