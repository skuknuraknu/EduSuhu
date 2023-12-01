package com.usk.ac.id.uas

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
        var user = auth.currentUser
        if ( user != null) {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
        }
        loginButton = findViewById<Button?>(R.id.buttonSignIn)
        loginButton.setOnClickListener(View.OnClickListener {
            LoginProcess()
        })
    }
    private fun LoginProcess(){
        email = findViewById<EditText>(R.id.editTextEmail).text.toString()
        password = findViewById<EditText>(R.id.editTextPassword).text.toString()
        if ( email.isNotEmpty() && password.isNotEmpty() ) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d("SignIn", "signInWithEmail:success")
                        val user = auth.currentUser
                        Toast.makeText(this, "Authentication successful.", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        var email_user = ""
                        var name_user = ""
                        var photo_user = ""
                        if ( user != null) {
                            email_user = user.email.toString()
                            name_user = email_user.split("@")[0]
                            photo_user = user.photoUrl.toString()
                            Log.d("USERINFO", photo_user)
                            intent.putExtra("email", email_user)
                            intent.putExtra("nama", name_user)
                            startActivity(intent)
                        }else {
                            Log.w("SignIn", "signInWithEmail:failure", task.exception)
                            Toast.makeText(baseContext, "User bermasalah.", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Log.w("SignIn", "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(baseContext, "Kolom email & password tidak boleh kosong", Toast.LENGTH_SHORT).show()
        }
    }

    fun goToRegister(view: View) {
        val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
        startActivity(intent)
    }

}