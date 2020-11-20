package com.example.movieapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_write_comment.*

class WriteCommentActivity : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    val user= FirebaseAuth.getInstance().currentUser
    var movieTitle:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_comment)


        var getIntent=getIntent()

        var intent1: Intent
        intent1 = Intent(this, MainActivity2::class.java)

        movieTitle=getIntent.getStringExtra("title")

        if (user != null) {
            // User is signed in
            Toast.makeText(
                baseContext, "auth success.",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            // No user is signed in
            Toast.makeText(
                baseContext, "auth failed.",
                Toast.LENGTH_SHORT
            ).show()
        }
        user?.let{
            val name=user.displayName
            val email=user.email
            val photoUrl=user.photoUrl
            val uid = user.uid
        }

        enrollButton.setOnClickListener {
            upload()
            intent1.putExtra("title",movieTitle)
            startActivity(intent1)
            finish()
        }

    }

    fun upload(){
        var comment:String
        comment=editText.text.toString()

        val data = hashMapOf(
            "email" to user!!.email,
            "comment" to comment,
            "title" to movieTitle
        )

        db.collection("comments")
            .add(data)
            .addOnSuccessListener { documentReference ->
            }
            .addOnFailureListener { e ->
            }

    }
}