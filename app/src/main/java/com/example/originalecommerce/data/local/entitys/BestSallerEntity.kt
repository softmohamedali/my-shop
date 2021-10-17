package com.example.originalecommerce.data.local.entitys

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.originalecommerce.models.Product
import com.example.originalecommerce.utils.Constants

@Entity(tableName = Constants.PRODUCT_best_TABLE_NAME)
class BestSallerEntity(
    var bestProduct:MutableList<Product>
) {
    @PrimaryKey(autoGenerate = false)
    var id:Int=0
}