package com.example.originalecommerce.data.local.entites

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.originalecommerce.models.Product
import com.example.originalecommerce.utils.Constants

@Entity(tableName = Constants.PRODUCT_TABLE_NAME)
class ProductsEntity(
        var products:MutableList<Product>
) {
    @PrimaryKey(autoGenerate = false)
    var id:Int=0
}