package com.example.originalecommerce.models

import android.os.Parcelable
import com.example.originalecommerce.data.local.entitys.OrderEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class AllREquests (var requests:MutableList<OrderEntity>?=null):Parcelable{
}