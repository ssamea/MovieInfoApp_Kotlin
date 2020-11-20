package com.example.movieapp

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.reflect.KFunction1

//object 사용한 이유 : 싱글톤(충돌방지) 선언이 쉬워서.
object MoviesRepository {

    private val api: Api

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create()) //서버에서 응답한 Json을 변환
            .build()

        api = retrofit.create(Api::class.java)
    } //retrofit 인스턴스화, 초기화

    fun getPopularMovies(
        page: Int = 1,
        onSuccess: (movies: List<Movie>) -> Unit, //영화 리스트를 받는 고차함수
        onError: () -> Unit //나중에 invoke 함
    ) {
        api.getPopularMovies(page = page)
            .enqueue(object : Callback<GetMoviesResponse> {
                override fun onResponse(
                    call: Call<GetMoviesResponse>,
                    response: Response<GetMoviesResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.movies)
                            //정상응답
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }

    fun getTopRatedMovies(page: Int = 1, onSuccess: (movies: List<Movie>) -> Unit, //영화 리스트를 받는 고차함수
        onError: () -> Unit //나중에 invoke 함
    ) {
        api.getTopRatedMovies(page = page)
            .enqueue(object : Callback<GetMoviesResponse> {
                override fun onResponse(
                    call: Call<GetMoviesResponse>,
                    response: Response<GetMoviesResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.movies)
                            //정상응답
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }

    fun getUpcomingMovies(
        page: Int = 1,
        onSuccess: (movies: List<Movie>) -> Unit, //영화 리스트를 받는 고차함수
        onError: () -> Unit //나중에 invoke 함
    ) {
        api.getUpcomingMovies(page = page)
            .enqueue(object : Callback<GetMoviesResponse> {
                override fun onResponse(
                    call: Call<GetMoviesResponse>,
                    response: Response<GetMoviesResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.movies)
                            //정상응답
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }

    fun getVideo(movie_id: Long, onSuccess: (videos : List<Video>) -> Unit, onError: () -> Unit) {
        val movieID = movie_id
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        val service = retrofit.create(Api::class.java)
        val call = service.getVideoTrailer(movieID = movieID)
        call.enqueue(object : Callback<GetVideoResponse> {
            override fun onResponse(call: Call<GetVideoResponse>, response: Response<GetVideoResponse>) {
                if(response.isSuccessful) {
                    val responseBody = response.body()
                    Log.d("response", responseBody.toString())

                    if (responseBody!!.videos.isNotEmpty() || responseBody.videos.size > 0){
                        onSuccess.invoke(responseBody.videos)
                    } else {
                        onError.invoke()
                    }
                } else {
                    Log.d("Result", "응답이 없습니다.")
                }
            }

            override fun onFailure(call: retrofit2.Call<GetVideoResponse>, t: Throwable) {
                Log.d("Error", "오류가 발생했습니다.")
            }
        })
    }
}