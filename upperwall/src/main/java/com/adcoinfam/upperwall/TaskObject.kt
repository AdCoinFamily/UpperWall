package com.adcoinfam.upperwall

data class TaskObject(
    val title : String,
    val message : String,
    val link : String,
    val price : Double,
    val id : String,
    val type : String,
    var checked : Boolean = false
)
