package com.example.originalecommerce.data.local.entitys

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.originalecommerce.models.Product
import com.example.originalecommerce.utils.Constants

@Entity(tableName = Constants.PRODUCT_ORDER_TABLE_NAME)
class OrderEntity (
    var prodct: Product?=Product(),
    var count:Int?=0,
    var timeOrder:String?="",
    var price:Float?=0f,
    var totalPrice:Float?=0f,
    var colors:String?="",
    var sizeprod:String?="",
    @PrimaryKey(autoGenerate = true)
    var id:Int?=null
) {

}