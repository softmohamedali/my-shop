package com.example.originalecommerce.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.originalecommerce.data.local.entites.ProductsEntity

@Database(
        entities = arrayOf(ProductsEntity::class),
        version = 1,
        exportSchema = false
)
@TypeConverters(MyTypeConverter::class)
abstract class DataBase:RoomDatabase() {
    abstract fun getDao():Dao
}