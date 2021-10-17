package com.example.originalecommerce.data.local.entitys

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.originalecommerce.models.Product
import com.example.originalecommerce.utils.Constants

@Entity(tableName = Constants.PRODUCT_FAV_TABLE_NAME)
data class FavEntity(
    var prod:Product,
    @PrimaryKey(autoGenerate = true)
    var id:Int?=null
) {

}