package com.sas.hackathonsocs

import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.sas.hackathonsocs.model.User
import kotlinx.android.synthetic.main.activity_otp.*
import kotlinx.android.synthetic.main.activity_register.*
import java.util.concurrent.TimeUnit

class OtpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    val TIME_OUT = 50
    var mCallback: PhoneAuthProvider.OnVerificationStateChangedCallbacks? = null
    private var state = 1 // 1 = login, 0 = register
    private var phoneNumber : String = ""
    private lateinit var user : User
    private lateinit var db:FirebaseFirestore
    private var verificationCode = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        val intent = this.intent
        if(intent.extras!!.get("phoneLogin") != null)
        {
            phoneNumber = ""+intent.extras!!.get("phoneLogin")
            Toast.makeText(this,phoneNumber,Toast.LENGTH_SHORT).show()
            state = 1
        }

        if (intent.extras!!.get("phoneRegister") != null)
        {
            user = intent.extras!!.get("phoneRegister") as User
            phoneNumber = user.phoneNumber
            Toast.makeText(this,phoneNumber,Toast.LENGTH_SHORT).show()
            state = 0
        }

        resend_otp_text.paintFlags = Paint.UNDERLINE_TEXT_FLAG


        mCallback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {

            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(this@OtpActivity, "Verifikasi gagal", Toast.LENGTH_SHORT).show()
                Log.d("FirebaseException", e.toString())
            }

            override fun onCodeSent(
                s: String,
                forceResendingToken: PhoneAuthProvider.ForceResendingToken
            ) {
                super.onCodeSent(s, forceResendingToken)
                verificationCode = s
                Log.d("verificationCode", verificationCode)
            }


        }
        sendOTP()
        resend_otp_text.setOnClickListener()
        {
            sendOTP()

        }
        continue_btn.setOnClickListener()
        {
            val credential = PhoneAuthProvider.getCredential(verificationCode,otp_code_field.text.toString())
            auth.signInWithCredential(credential).addOnCompleteListener()
            {
                if (it.isSuccessful)
                {
                    if (state == 0)
                    {
                        // do regis
                        db.collection("users").document(user.email).set(user).addOnSuccessListener {
                            Toast.makeText(this,"Pendaftaran Berhasil",Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener()
                        {
                            Toast.makeText(this,""+it.message,Toast.LENGTH_SHORT).show()
                        }
                    }
                    else {
                        // do login
                        db.collection("users").whereEqualTo("phoneNumber",phoneNumber).get().addOnSuccessListener {
                            if (it.size() == 0 )
                            {
                                Toast.makeText(this,"Akun tidak ditemukan",Toast.LENGTH_SHORT).show()
                            }
                            else{
                                // go to user dashboard
                                db.collection("users").document(user.email)
                            }
                        }
                    }
                }
                else
                {
                    Toast.makeText(this,"Kode verifikasi gagal, coba kembali",Toast.LENGTH_SHORT).show()
                }
            }


            }


    }

    private fun sendOTP(){
        Thread({
            var i = 60;
            resend_otp_text.isEnabled = false
            while(i > 0)
            {
                resend_otp_text.text = getString(R.string.resend_otp)+" (${i}) "
                i--;
                Thread.sleep(1000)
            }
            resend_otp_text.text = getString(R.string.resend_otp)
            resend_otp_text.isEnabled = true

        }).start()
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            "+62"+phoneNumber,// Phone number to verify
            TIME_OUT.toLong(), // Timeout duration
            TimeUnit.SECONDS,  // Unit of timeout
            this@OtpActivity, // Activity (for callback binding)
            mCallback!!
        )
    }
}