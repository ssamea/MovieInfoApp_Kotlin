package com.example.movieapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class ListAdapter(val context: Context, val CommentList: ArrayList<Comment>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View=LayoutInflater.from(context).inflate(R.layout.write_comment,null)
        val Subject=view.findViewById<TextView>(R.id.subject)
        val Comment=view.findViewById<TextView>(R.id.comment)

        val comment = CommentList[position]

        //후기정보 불러오기
        //사용자 UID로 구분하여 가져옴

        Subject.text=comment.email
        Comment.text=comment.comment

        return view
    }
    override fun getItem(position: Int): Any {
        return CommentList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return CommentList.size
    }

}