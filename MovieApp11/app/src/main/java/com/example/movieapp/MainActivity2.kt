package com.example.movieapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {

    //카드뷰와 리스트뷰를 연결하기 위한 코드

    var db = FirebaseFirestore.getInstance()
    var movieTitle:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        var getIntent=getIntent()

        movieTitle= getIntent.getStringExtra("title")


        var intent1: Intent
        intent1 = Intent(this, WriteCommentActivity::class.java)

        getCommentData()

        //카드뷰와 리스트뷰를 연결하기 위한 코드


        editTextEnroll.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        intent1.putExtra("title",movieTitle)
                        startActivity(intent1)
                        finish()
                    }
                }

                return true
            }
        })
    }

    fun getCommentData(){

        var CommentList_inFun = arrayListOf<Comment>()
        //회원정보 불러오기
        db.collection("comments")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    if(document.getData().get("title").toString()==movieTitle){
                        CommentList_inFun.add(
                            Comment(
                                document.getData().get("email").toString(),
                                document.getData().get("comment").toString()
                            )
                        )
                    }
                    else{
                        //딱히
                    }
                    var Adapter = ListAdapter(this, CommentList_inFun)
                    list_view.adapter = Adapter
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(
                    baseContext, "Show List Failed.",
                    Toast.LENGTH_SHORT
                ).show()
            }

        //return CommentList_inFun
    }


}