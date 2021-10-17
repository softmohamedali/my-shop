package com.example.originalecommerce.models

import com.example.originalecommerce.data.local.entitys.OrderEntity

data class Paymnts(
    var orders:MutableList<OrderEntity>?= mutableListOf(),
    var name:String?="",
    var phone:String?="",
    var Location:String?="",
    var time:String?="",
    var statusPayments:Int?=1,
    var id:String?=null
) {
}