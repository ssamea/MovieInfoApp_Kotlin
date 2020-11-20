package com.example.movieapp

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*


//Employess 클래스= 데이터 클래스=noticedata
class noticeActivity : AppCompatActivity() {
    // [START declare_database_ref]
    //lateinit var  ref: DatabaseReference
    lateinit var  itemList: MutableList<noticedata>
    lateinit var  listview: ListView

    //실제 변수를 초기화한 후 공지사항의 목록을 가지고 있을 ArrayList
  //  var itemList = arrayListOf<noticedata>()


    private var mDatabase: FirebaseDatabase? = null
    private var mReference: DatabaseReference? = null
    private var mChild: ChildEventListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice)

        itemList= mutableListOf()
        listview= findViewById(R.id.listview)// 리스트뷰 연결결
        initDatabase()

        mDatabase = FirebaseDatabase.getInstance()
        mReference = mDatabase!!.getReference("Notice/")
       // ref=FirebaseDatabase.getInstance().getReference("Notice/")

        mReference!!.addValueEventListener(object  :ValueEventListener{


            override fun onDataChange(p0: DataSnapshot) {
                if(p0!!.exists()){
                    itemList.clear()
                    for(e in p0.children){
                        val noticement=e.getValue(noticedata::class.java)
                        itemList.add(noticement!!)
                    }


                    val adapter=NoticeAdapter(this@noticeActivity, itemList as ArrayList<noticedata>)
                    listview.adapter=adapter
                }


            }

            override fun onCancelled(p0: DatabaseError) {

            }

        })

    }
    private fun initDatabase() {
        mDatabase = FirebaseDatabase.getInstance()
        mReference = mDatabase!!.getReference("Notice/")
        mChild = object : ChildEventListener {
            override fun onChildAdded(p0: DataSnapshot, s: String?) {}
            override fun onChildChanged(p0: DataSnapshot, s: String?) {}
            override fun onChildRemoved(p0: DataSnapshot) {}
            override fun onChildMoved(p0: DataSnapshot, s: String?) {}
            override fun onCancelled(p0: DatabaseError) {}
        }
        mReference!!.addChildEventListener(mChild as ChildEventListener)
    }
}
