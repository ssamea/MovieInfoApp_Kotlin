package com.example.movieapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.GsonBuilder
import okhttp3.*

class infoActivity : AppCompatActivity() {
    //로그 아웃
    private val buttonLogout: Button? = null
    //private TextView textViewUserEmail;

    //firebase auth object
    private var firebaseAuth: FirebaseAuth? = null

    val clientId = "client id"
    val clientSecret = "client secret"

    private lateinit var popularMovies: RecyclerView
    private lateinit var popularMoviesAdapter: MoviesAdapter

    private lateinit var topRatedMovies: RecyclerView
    private lateinit var topRatedMoviesAdapter: MoviesAdapter

    private lateinit var upcomingMovies: RecyclerView
    private lateinit var upcomingMoviesAdapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        //initializing firebase authentication object

        //buttonLogout = (Button) findViewById(R.id.buttonLogout);

        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance()
        //유저가 로그인 하지 않은 상태라면 null 상태이고 이 액티비티를 종료하고 로그인 액티비티를 연다.
        if (firebaseAuth!!.getCurrentUser() == null) {
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }

        //인기영화 리싸이클러 뷰--------------------------------------------

        popularMovies = findViewById(R.id.popular_movies)

        popularMovies.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        popularMoviesAdapter = MoviesAdapter(listOf())
        popularMovies.adapter = popularMoviesAdapter

        MoviesRepository.getPopularMovies(
            onSuccess = ::onPopularMoviesFetched, //고차함수 대입(매개변수)
            onError = ::onError //고차함수 대입(매개변수)
        )
        //----------------------------------------------------------------

        //영화 점유율 리싸이클러 뷰------------------------------------------
        //리싸이클러뷰 할당
        topRatedMovies = findViewById(R.id.top_rated_movies)

        //레이아웃 매니저 필수
        //수평방향
        topRatedMovies.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        topRatedMoviesAdapter = MoviesAdapter(listOf())
        topRatedMovies.adapter = topRatedMoviesAdapter

        MoviesRepository.getTopRatedMovies(
            onSuccess = ::onTopRatedMoviesFetched, //고차함수 대입(매개변수)
            onError = ::onError //고차함수 대입(매개변수)
        )
        //----------------------------------------------------------------

        upcomingMovies = findViewById(R.id.upcoming_movies)

        //레이아웃 매니저 필수
        //수평방향
        upcomingMovies.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        upcomingMoviesAdapter = MoviesAdapter(listOf())
        upcomingMovies.adapter = upcomingMoviesAdapter

        MoviesRepository.getUpcomingMovies(
            onSuccess = ::onUpcomingMoviesFetched,
            onError = ::onError
        )

    }

    private fun onPopularMoviesFetched(movies: List<Movie>) {
        popularMoviesAdapter.updateMovies(movies)
    }

    private fun onTopRatedMoviesFetched(movies: List<Movie>) {
        topRatedMoviesAdapter.updateMovies(movies)
    }

    private fun onUpcomingMoviesFetched(movies: List<Movie>) {
        upcomingMoviesAdapter.updateMovies(movies)
    }

    private fun onError() {
        Toast.makeText(this, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                firebaseAuth!!.signOut()
                finish()
                startActivity(Intent(this, MainActivity::class.java))
                Toast.makeText(this, "로그아웃 되었습니다!", Toast.LENGTH_SHORT).show()
                return true
            }

            R.id.button_search->{
                startActivity(Intent(this, SearchActivity::class.java))
                Toast.makeText(this, "원하는 영화를 검색하세요!", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.notice->{
                startActivity(Intent(this, noticeActivity::class.java))
                Toast.makeText(this,  "공지를 확인 하세요!", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
