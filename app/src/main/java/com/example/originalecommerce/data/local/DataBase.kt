package com.example.originalecommerce.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.originalecommerce.data.local.entitys.*
import com.example.originalecommerce.models.Product
import com.example.orignal_ecommerce_manger.models.Catigory

@Database(
        entities = arrayOf(
            BestSallerEntity::class,
            CatigoryEntity::class,
            OfferEntity::class,
            FavEntity::class,
            OrderEntity::class,
            NotificationEntity::class
        ),
        version =5,
        exportSchema = false
)
@TypeConverters(MyTypeConverter::class)
abstract class DataBase:RoomDatabase() {
    abstract fun getDao():Dao
}