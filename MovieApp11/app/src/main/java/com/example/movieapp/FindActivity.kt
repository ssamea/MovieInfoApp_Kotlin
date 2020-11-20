package com.example.movieapp

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class FindActivity : AppCompatActivity() {


    private val TAG = "FindActivity"

    //define view objects
    private var editTextUserEmail: EditText? = null
    private var buttonFind: Button? = null
    private val textviewMessage: TextView? = null
    private var progressDialog: ProgressDialog? = null

    //define firebase object
    private var firebaseAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find)


        editTextUserEmail = findViewById<View>(R.id.movieName) as EditText
        buttonFind = findViewById<View>(R.id.buttonFind) as Button
        progressDialog = ProgressDialog(this)
        firebaseAuth = FirebaseAuth.getInstance()

        buttonFind!!.setOnClickListener{

                progressDialog!!.setMessage("처리중입니다. 잠시 기다려 주세요...")
                progressDialog!!.show()
                //비밀번호 재설정 이메일 보내기
                val emailAddress =
                    editTextUserEmail!!.text.toString().trim { it <= ' ' }
                firebaseAuth!!.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this@FindActivity, "이메일을 보냈습니다.", Toast.LENGTH_LONG)
                                .show()
                            finish()
                            startActivity(
                                Intent(
                                    applicationContext,
                                    MainActivity::class.java
                                )
                            )
                        } else {
                            Toast.makeText(this@FindActivity, "메일 보내기 실패!", Toast.LENGTH_LONG)
                                .show()
                        }
                        progressDialog!!.dismiss()

            }
        }
    }
}
