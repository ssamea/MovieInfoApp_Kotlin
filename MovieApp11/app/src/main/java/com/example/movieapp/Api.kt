package com.example.movieapp

import android.telecom.Call
import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String="7f1c8e87d59142f90264f04d8fa0f51c",
        @Query("page") page: Int
        ,@Query("language") language : String= "ko"
    ): retrofit2.Call<GetMoviesResponse>

    @GET("movie/top_rated") //높은 점유율 영화 불러오기 GET 요청
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String="7f1c8e87d59142f90264f04d8fa0f51c",
        //apiKey는 초기화 -> 매개변수에 page 수만 입력하면됨.
        @Query("page") page: Int //페이지 파라미터
        ,@Query("language") language : String= "ko"
    ): retrofit2.Call<GetMoviesResponse>

    //Upcoming movies
    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key") apiKey: String = "7f1c8e87d59142f90264f04d8fa0f51c",
        @Query("page") page : Int,
        @Query("language") language : String = "ko"
    ): retrofit2.Call<GetMoviesResponse>

    //Trailer
    @GET("movie/{movie_id}/videos")
    fun getVideoTrailer(
        @Path("movie_id") movieID: Long, //선택된 movie id
        @Query("api_key") apiKey: String = "7f1c8e87d59142f90264f04d8fa0f51c",
        @Query("language") language : String = "ko"
    ):retrofit2.Call<GetVideoResponse>

} //Retrofit에서 사용할 API를 정의한 인터페이스


