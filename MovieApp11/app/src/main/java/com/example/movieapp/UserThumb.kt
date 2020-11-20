package com.example.movieapp

import java.io.Serializable

data class UserThumb(
    var num:Int?=0,
    var email:List<String>?=null

) : Serializable