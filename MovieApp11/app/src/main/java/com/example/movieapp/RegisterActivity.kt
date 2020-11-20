package com.example.movieapp

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    //define view objects
    var editTextEmail: EditText? = null
    var editTextPassword: EditText? = null
    var buttonSignup: Button? = null
    var textviewSingin: TextView? = null
    var textviewMessage: TextView? = null
    var progressDialog: ProgressDialog? = null

    //define firebase object
    var firebaseAuth: FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        //initializig firebase auth object
        firebaseAuth = FirebaseAuth.getInstance()

        if (firebaseAuth!!.currentUser != null) {
            //이미 로그인 되었다면 이 액티비티를 종료함
            finish()
            //그리고 챗봇 액티비티를 연다.
            startActivity(Intent(applicationContext,infoActivity::class.java)) //추가해 줄 ProfileActivity
        }


        //initializing views회어
        editTextEmail = findViewById<View>(R.id.editTextEmail) as EditText
        editTextPassword = findViewById<View>(R.id.editTextPassword) as EditText
        textviewSingin = findViewById<View>(R.id.textViewSignin) as TextView
        textviewMessage = findViewById<View>(R.id.textviewMessage) as TextView
        buttonSignup = findViewById<View>(R.id.buttonSignup) as Button
        progressDialog = ProgressDialog(this)


        //button click event
        buttonSignup!!.setOnClickListener{
            registerUser()
        }
        textviewSingin!!.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java)) //추가해 줄 로그인 액티비티
        }
    }

    //Firebse creating a new user
    private fun registerUser() {
        //사용자가 입력하는 email, password를 가져온다.
        val email = editTextEmail!!.text.toString()
        val password = editTextPassword!!.text.toString()

        //email과 password가 비었는지 아닌지를 체크 한다.
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Email을 입력해 주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Password를 입력해 주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        //email과 password가 제대로 입력되어 있다면 계속 진행된다.
        progressDialog!!.setMessage("등록중입니다. 기다려 주세요...")
        progressDialog!!.show()

        //유저 아이디 비번 만들기
        firebaseAuth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                 if (task.isSuccessful) {
                    Log.d("TAG", "signInWithEmail:success")
                    startActivity(Intent(applicationContext, infoActivity::class.java))
                    finish()
                }

                else {
                    //에러발생시
                    textviewMessage!!.text = "에러유형\n - 이미 등록된 이메일  \n -암호 최소 6자리 이상 \n - 서버에러"
                    Toast.makeText(this@RegisterActivity, "등록 에러!", Toast.LENGTH_SHORT).show()
                }
                progressDialog!!.dismiss()
            }
    }


}
