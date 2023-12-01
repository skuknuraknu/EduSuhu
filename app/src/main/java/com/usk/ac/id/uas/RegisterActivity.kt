package com.usk.ac.id.uas

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private lateinit var email:String
    private lateinit var password:String
    private lateinit var Auth: FirebaseAuth
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        Auth = FirebaseAuth.getInstance()
        findViewById<Button>(R.id.buttonRegister).setOnClickListener(View.OnClickListener {
            RegisterProcess()
        })
    }
    private fun RegisterProcess() {
        email = findViewById<EditText>(R.id.editTextEmail).text.toString()
        password = findViewById<EditText>(R.id.editTextPassword).text.toString()
        Auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this, "Register Berhasil",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        this, "Register Gagal " +
                                task.exception?.message, Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    fun goToLogin(view: View) {
        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
        startActivity(intent)
    }
}