package com.example.originalecommerce.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    var img:String?=null,
    var name:String?=null,
    var catigory:String?=null,
    var count:String?=null,
    var buyPrice:String?=null,
    var salePrice:String?=null,
    var offer:String?=null,
    var Rating:String?=null,
    var description:String?=null,
    var id:String?=null,
    var isOffers:String?=null,
    var isBestSaller:String?=null,
    var isRecommended:String?=null,
    var sizeAvilable:String?=null,
    var colorAvialable:MutableList<String>?=null
):Parcelable {
}