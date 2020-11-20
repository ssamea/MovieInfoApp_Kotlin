package com.example.movieapp

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.movieapp.MoviesRepository.getVideo
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.annotations.SerializedName
import kotlinx.android.synthetic.main.activity_info1.*
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.activity_write_comment.*

const val MOVIE_BACKDROP = "extra_movie_backdrop"
const val MOVIE_POSTER = "extra_movie_poster"
const val MOVIE_TITLE = "extra_movie_title"
const val MOVIE_RATING = "extra_movie_rating"
const val MOVIE_RELEASE_DATE = "extra_movie_release_date"
const val MOVIE_OVERVIEW = "extra_movie_overview"
const val MOVIE_ID="extra_movie_id" //후
var VIDEO_ID=""// 이거 이상해

class ThumbNum(var thumbUp:Int, var thumbDown:Int) {
}

class Info1 : YouTubeBaseActivity(),YouTubePlayer.OnInitializedListener{
    private lateinit var poster: ImageView
    private lateinit var title: TextView
    private lateinit var rating: RatingBar
    private lateinit var releaseDate: TextView
    private lateinit var overview: TextView

    val db = FirebaseFirestore.getInstance()
    val user= FirebaseAuth.getInstance().currentUser
    var thumb_num= ThumbNum(0,0)
    var thumbUpLocal: Int=0
    var thumbDownLocal: Int=0
    var userthumb = UserThumb()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info1)

        var intent2: Intent
        intent2= Intent(this,MainActivity2::class.java)

        //poster = findViewById(R.id.movie_poster)
        title = findViewById(R.id.movie_title)
        rating = findViewById(R.id.movie_rating)
        releaseDate = findViewById(R.id.movie_release_date)
        overview = findViewById(R.id.movie_overview)

        val extras = intent.extras

        if (extras != null) {
            populateDetails(extras)
        }
        /*
        get_thumb_up(title.text.toString())
        get_thumb_down(title.text.toString())
        thumbUpLocal=thumb_num.thumbUp

        thumbDownLocal=thumb_num.thumbDown

        thumb_up.setOnClickListener{
            thumbUpLocal++
            thum_up_num.text=thumbUpLocal.toString()
            update_thumbUp(title.text.toString(),thumbUpLocal,thumbDownLocal)
            thumb_up.setImageResource(R.drawable.ic_thumb_up_selected)
        }

        thumb_down.setOnClickListener {
            thumbDownLocal++
            thumb_down_num.text= thumbDownLocal.toString()
            update_thumbDown(title.text.toString(),thumbUpLocal,thumbDownLocal)
            thumb_down.setImageResource(R.drawable.ic_thumb_down_selected)
        }
        
         */

        intent2.putExtra("title",extras!!.getString(MOVIE_TITLE,""))

        see_opinion.setOnClickListener {
            startActivity(intent2)
            //finish()
        }
    }

    private fun populateDetails(extras: Bundle) {

        title.text = extras.getString(MOVIE_TITLE, "")
        rating.rating = extras.getFloat(MOVIE_RATING, 0f) / 2
        releaseDate.text = extras.getString(MOVIE_RELEASE_DATE, "")
        overview.text = extras.getString(MOVIE_OVERVIEW, "")
        var MOVIE_ID = extras.getLong((MOVIE_ID)) //
        Log.d("intfo1", "movieid: $MOVIE_ID")


        //유튜브에서 가져올 비디오의 키를 얻어 내서 화면에 해당 영상을 재생하도록 한다.
        getVideo(MOVIE_ID, onSuccess = :: onVideosFetched, onError =  :: onError)

    }


    private fun onVideosFetched(videos: List<Video>){
        Log.d("intfo1", "Youtube: ${videos[0].video_key}")


        if (videos[0].video_key != null) {

            VIDEO_ID = videos[0].video_key //에러

            //포스터 이미지 대신 트레일러 영상을 보여 줌
            yt_pv.initialize("AIzaSyCnHjH3M2N0grX_aP0aCwCBGA3LAgujarQ", this@Info1) //AIzaSyCnHjH3M2N0grX_aP0aCwCBGA3LAgujarQ
        }
    }

    private fun onError(){
        //유튜브 뷰는 안보이게 하고 포스터 이미지를 보여 줌
        yt_pv.visibility = View.GONE
    }

    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider?,
        player: YouTubePlayer?,
        wasStored: Boolean
    ) {
        if(!wasStored){
            player?.cueVideo(VIDEO_ID)
        }
    }

    override fun onInitializationFailure(
        p0: YouTubePlayer.Provider?,
        p1: YouTubeInitializationResult?
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun get_thumb_up(title:String){

        db.collection(title).document("thumbup")
            .get()
            .addOnSuccessListener { document ->
                thumb_num.thumbUp = document.getData()!!.get("num").toString().toInt()
                //thumb_num.thumbDown = document.getData()!!.get("thumbdown").toString().toInt()

                thum_up_num.text=thumb_num.thumbUp.toString()
                //thumb_down_num.text=thumb_num.thumbDown.toString()

                thumbUpLocal=thumb_num.thumbUp
                //thumbDownLocal=thumb_num.thumbDown



                /*
                Toast.makeText(
                    baseContext, "${thumb_num.thumbUp}",
                    Toast.LENGTH_SHORT
                ).show()
                 */
            }


    }

    fun get_thumb_down(title:String){

        db.collection(title).document("thumbdown")
            .get()
            .addOnSuccessListener { document ->
                //thumb_num.thumbUp = document.getData()!!.get("num").toString().toInt()
                thumb_num.thumbDown = document.getData()!!.get("num").toString().toInt()

                //thum_up_num.text=thumb_num.thumbUp.toString()
                thumb_down_num.text=thumb_num.thumbDown.toString()

                //thumbUpLocal=thumb_num.thumbUp
                thumbDownLocal=thumb_num.thumbDown



                /*
                Toast.makeText(
                    baseContext, "${thumb_num.thumbUp}",
                    Toast.LENGTH_SHORT
                ).show()
                 */
            }


    }

    fun update_thumbUp(title:String,thumbup:Int,thumbdown:Int){

        var data=hashMapOf(
            "thumbup" to thumbup,
            "thumbdown" to thumbdown
        )

        var thumbData=hashMapOf(
            title to "thumbup"
        )
        db.collection(title).document("thumbup")
            .get()
            .addOnSuccessListener { document->
                userthumb.email=document.getData()!!.get("email") as List<String>
            }
        //userthumb.email=user!!.email
        userthumb.num=thumbup

        db.collection(title).document("thumbup")
            .set(userthumb)

    }

    fun update_thumbDown(title:String,thumbup:Int,thumbdown:Int){

        var data=hashMapOf(
            "thumbup" to thumbup,
            "thumbdown" to thumbdown
        )

        var thumbData=hashMapOf(
            title to "thumbdown"
        )

        //userthumb.email=user!!.email
        userthumb.num=thumbdown

        db.collection(title).document("thumbdown")
            .set(userthumb)


    }


}


//데이터 클래스 정의
data class Video(
    @SerializedName("id") val vide_id : String, //@SerialzedName 은 변수명을 다르게 하고 싶을때 사용
    @SerializedName("key") val video_key : String,
    @SerializedName("name") val video_name : String,
    @SerializedName("size") val video_size : Int
)


data class GetVideoResponse(
    @SerializedName("id") val id : Int,
    @SerializedName("results") val videos: List<Video>
)


