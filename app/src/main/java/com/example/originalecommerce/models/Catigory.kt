package com.example.orignal_ecommerce_manger.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.originalecommerce.utils.Constants


data class Catigory(
    var name:String?=null,
    var img:String?=null,
    var id:String?=null
) {
}