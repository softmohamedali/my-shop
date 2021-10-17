package com.example.originalecommerce.data.local

import androidx.room.TypeConverter
import com.example.originalecommerce.models.Product
import com.example.orignal_ecommerce_manger.models.Catigory
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MyTypeConverter {
    val json= Gson()

    @TypeConverter
    fun catEntityTOString(cat:MutableList<Catigory>):String
    {
        return json.toJson(cat)
    }

    @TypeConverter
    fun StringTOCatEntity(string:String):MutableList<Catigory>
    {
        return json.fromJson(string,object : TypeToken<MutableList<Catigory>>(){}.type)
    }

    @TypeConverter
    fun prodEntityTOString(prod:MutableList<Product>):String
    {
        return json.toJson(prod)
    }

    @TypeConverter
    fun StringTOProdEntity(string:String):MutableList<Product>
    {
        return json.fromJson(string,object : TypeToken<MutableList<Product>>(){}.type)
    }

    @TypeConverter
    fun favTOString(fav:Product):String
    {
        return json.toJson(fav)
    }

    @TypeConverter
    fun StringTOfav(string:String):Product
    {
        return json.fromJson(string,object : TypeToken<Product>(){}.type)
    }



}