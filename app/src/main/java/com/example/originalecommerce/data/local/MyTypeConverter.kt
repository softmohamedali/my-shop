package com.example.originalecommerce.data.local

import androidx.room.TypeConverter
import com.example.originalecommerce.models.Product
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MyTypeConverter {
    val json= Gson()


    @TypeConverter
    fun ProductTOString(products:MutableList<Product>):String
    {
        return json.toJson(products)
    }

    @TypeConverter
    fun StringTOProduct(string:String):MutableList<Product>
    {
        return json.fromJson(string,object : TypeToken<MutableList<Product>>(){}.type)
    }
}