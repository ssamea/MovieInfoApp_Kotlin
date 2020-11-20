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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var editTextEmail: EditText? = null
    var editTextPassword: EditText? = null
    var buttonSignin: Button? = null
    var textviewSingin1 //회원가입 텍스ㅡ뷰
            : TextView? = null
    var textviewMessage: TextView? = null
    var textviewFindPassword: TextView? = null
    var progressDialog: ProgressDialog? = null

    //define firebase object
    var firebaseAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initializig firebase auth object
        firebaseAuth = FirebaseAuth.getInstance()

        if (firebaseAuth!!.currentUser != null) {
            //이미 로그인 되었다면 이 액티비티를 종료함
            finish()
            //그리고 chatbot 액티비티를 연다.
            startActivity(Intent(applicationContext, infoActivity::class.java))
        }


        //initializing views
        editTextEmail = findViewById<View>(R.id.editTextEmail) as EditText
        editTextPassword = findViewById<View>(R.id.editTextPassword) as EditText
        textviewSingin1 = findViewById<View>(R.id.textViewSignin) as TextView
        textviewMessage = findViewById<View>(R.id.textviewMessage) as TextView
        textviewFindPassword =
            findViewById<View>(R.id.textViewFindpassword) as TextView
        buttonSignin = findViewById<View>(R.id.buttonSignup) as Button
        progressDialog = ProgressDialog(this)



        //button click event
        buttonSignin!!.setOnClickListener(){
            userLogin()
        }
       textviewSingin1!!.setOnClickListener(){
           //  회원가입
           finish()
           val intent1: Intent
           intent1 = Intent(this, RegisterActivity::class.java)
           startActivity(intent1)

       }
        textviewFindPassword!!.setOnClickListener(){
            finish()
            val intent2: Intent
            intent2 = Intent(this, FindActivity::class.java)
            startActivity(intent2)

        }
    }

    private fun userLogin() {
        val email = editTextEmail!!.text.toString().trim { it <= ' ' }
        val password = editTextPassword!!.text.toString().trim { it <= ' ' }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "email을 입력해 주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "password를 입력해 주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        progressDialog!!.setMessage("로그인중입니다. 잠시 기다려 주세요...")
        progressDialog!!.show()

        //user log in
        firebaseAuth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    finish()
                    startActivity(Intent(applicationContext, infoActivity::class.java))

                } else {
                    Toast.makeText(applicationContext, "로그인 실패!!", Toast.LENGTH_LONG)
                        .show()
                    textviewMessage!!.text = "로그인 실패 유형\n - id와 papssword를 확인해주세요!!\n "

                }


            }

    }


}
