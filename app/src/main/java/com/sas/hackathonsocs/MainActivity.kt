package com.sas.hackathonsocs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.android.synthetic.main.activity_main.*
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.sas.hackathonsocs.model.User

class MainActivity : AppCompatActivity() {

    private lateinit var gso: GoogleSignInOptions
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        login_btn.setOnClickListener()
        {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
        register_btn.setOnClickListener()
        {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }
        login_google_btn.setOnClickListener()
        {

            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, 1)
        }
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestScopes(Scope("https://www.googleapis.com/auth/user.phonenumbers.read"))
            .build()


        auth = FirebaseAuth.getInstance()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        auth.signOut()
        mGoogleSignInClient.revokeAccess()
        db = FirebaseFirestore.getInstance()

    }
    @Override
    protected override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            getData(task)
        }
    }

    private fun getData(task: Task<GoogleSignInAccount>){

        try{
            val acc  = task.getResult(ApiException::class.java)!!
            firebaseAuthWithGoogle(acc.idToken!!)
        }catch (ex: ApiException)
        {
            Toast.makeText(this, ex.message, Toast.LENGTH_SHORT).show()
        }
    }
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)

        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user : FirebaseUser = auth.currentUser!!
                    tryInsertData(user)
                } else {
                    Toast.makeText(this, "Authentication Failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun tryInsertData(user:FirebaseUser)
    {
        var phoneNumber = ""
        if(user.phoneNumber != null)
        {
            phoneNumber = user.phoneNumber!!
        }
        val newUser = User(user.displayName!!,phoneNumber,user.email!!,"Male")
        db.collection("users").document(newUser.email).set(newUser).addOnSuccessListener {
//            redirect to user dashboard
        }
    }

}