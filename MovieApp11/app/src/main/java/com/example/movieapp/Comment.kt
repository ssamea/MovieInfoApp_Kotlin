package com.example.movieapp

import java.io.Serializable

data class Comment(
    var email:String?=null,
    var comment:String?=null
) : Serializable