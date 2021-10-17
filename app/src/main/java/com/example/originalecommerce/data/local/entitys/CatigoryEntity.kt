package com.example.originalecommerce.data.local.entitys

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.originalecommerce.utils.Constants
import com.example.orignal_ecommerce_manger.models.Catigory


@Entity(tableName = Constants.Catigory_TABLE_NAME)
data class CatigoryEntity(
    var catigorys:MutableList<Catigory>
) {
    @PrimaryKey(autoGenerate = false)
    var id :Int=0
}