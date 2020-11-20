package com.example.movieapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop



var context:Context ?=null

class MoviesAdapter (
    private var movies: List<Movie> //movies 파라미터
) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {
    //반환값이 이너클래스 기반의 리싸이클러뷰 어댑터

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
            //카드뷰 인플레이트
        context=parent.getContext()
        return MovieViewHolder(view)
    } //뷰 홀더를 생성하고 뷰를 붙여주는 부분

    override fun getItemCount(): Int = movies.size

    //데이터 묶음
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])


        //이벤트리스너
        holder.itemView.setOnClickListener {

            val intent1: Intent
            intent1 = Intent(context, Info1::class.java)
            intent1.putExtra(MOVIE_BACKDROP,movies[position].backdropPath)
            intent1.putExtra(MOVIE_POSTER,movies[position].posterPath)
            intent1.putExtra(MOVIE_TITLE,movies[position].title)
            intent1.putExtra(MOVIE_RATING,movies[position].rating)
            intent1.putExtra(MOVIE_RELEASE_DATE,movies[position].releaseDate)
            intent1.putExtra(MOVIE_OVERVIEW,movies[position].overview)
            intent1.putExtra(MOVIE_ID, movies[position].id)

            context?.startActivity(intent1)


        } //재활용 되는 뷰가 호출해서 실행되는 메소드

    }

    fun updateMovies(movies: List<Movie>) {
        this.movies = movies //기존 데이터에서 업데이트
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val poster: ImageView = itemView.findViewById(R.id.item_movie_poster)

        fun bind(movie: Movie) {
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w342${movie.posterPath}")
                    //TMDb로부터 포스터 이미지 가져옴
                .transform(CenterCrop())
                .into(poster)
        }
    }
}