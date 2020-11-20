package com.example.movieapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class NoticeAdapter(val context: Context, val notice_List: ArrayList<noticedata>) : BaseAdapter() {
    //xml 파일의 View와 데이터를 연결
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        TODO("Not yet implemented")
        /* LayoutInflater는 item을 Adapter에서 사용할 View로 부풀려주는(inflate) 역할을 한다. */
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_notice, null) //item_notice의 텍스트뷰랑 연결해줘야함

        /* 위에서 생성된 view를 res-layout-item_notice의.xml 파일의  text View와 연결하는 과정이다. */
        val notice_item = view.findViewById<TextView>(R.id.tv_notice)

        /*변수 notice의  데이터를 TextView에 담는다. */
        val notice = notice_List[position] //
        notice_item.text=notice.notice

        return view
    }

    override fun getItem(position: Int): Any {
        TODO("Not yet implemented")
        return notice_List[position]
    }

    override fun getItemId(position: Int): Long {
        TODO("Not yet implemented")
        return 0
    }

    override fun getCount(): Int {
        TODO("Not yet implemented")
        return notice_List.size
    }
}