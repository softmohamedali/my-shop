package com.example.originalecommerce.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.originalecommerce.data.local.entites.ProductsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(productsEntity: ProductsEntity)

    @Query("SELECT * FROM PRODUCTS")
    fun getAllProduct():Flow<ProductsEntity>



}