package com.example.movieapp
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import android.content.Intent
import android.net.Uri
import kotlinx.android.synthetic.main.item_raw.view.*

class RecyclerViewAdapter(val homefeed: Homefeed):RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    //아이템의 갯수
    override fun getItemCount(): Int {
        return homefeed.items.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_raw, parent, false)
        return ViewHolder(v)
    }

    //데이터 묶음
    override fun onBindViewHolder(holder: RecyclerViewAdapter.ViewHolder, position: Int) {
        holder.bindItems(homefeed.items.get(position))
        val layoutParams = holder.itemView.layoutParams
        holder.itemView.requestLayout()
    }


    class ViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        fun bindItems(data : Item){
            //
            Glide.with(view.context).load(data.image)
                .apply(RequestOptions.centerCropTransform())
                .into(view.imageView)
            itemView.textView_title.text = data.title
            itemView.textView_actor.text = "출연 ${data.actor}"
            itemView.textView_director.text = "감독 ${data.director}"

            //클릭시 웹사이트 연결
            itemView.setOnClickListener {
                val webpage = Uri.parse("${data.link}")
                val webIntent = Intent(Intent.ACTION_VIEW, webpage)
                view.getContext().startActivity(webIntent);
            }
        }
    }

}