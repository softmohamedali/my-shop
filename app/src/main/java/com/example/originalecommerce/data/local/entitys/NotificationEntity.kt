package com.example.originalecommerce.data.local.entitys

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.originalecommerce.models.Product
import com.example.originalecommerce.utils.Constants

@Entity(tableName = Constants.NOTIFICATION_TABLE_NAME)
data class NotificationEntity(
    var msg:String,
    var product: Product,
    var time:String
) {
    @PrimaryKey(autoGenerate = true)
    var notiId:Int=0
}