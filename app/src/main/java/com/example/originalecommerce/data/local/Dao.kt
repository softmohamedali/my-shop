package com.example.originalecommerce.data.local

import androidx.room.*
import androidx.room.Dao
import com.example.originalecommerce.data.local.entitys.*
import com.example.originalecommerce.models.Product
import com.example.orignal_ecommerce_manger.models.Catigory
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCatigory(cati:CatigoryEntity)

    @Query("SELECT * FROM Catigorys")
    fun getAllCatigory():Flow<MutableList<CatigoryEntity>>

    @Query("DELETE FROM Catigorys")
    suspend fun deleteAllCatigory()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBestProds(prods:BestSallerEntity)

    @Query("SELECT * FROM best")
    fun getAllBestProd():Flow<MutableList<BestSallerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOfferProds(prods:OfferEntity)

    @Query("SELECT * FROM offer")
    fun getAllOfferProd():Flow<MutableList<OfferEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavProds(prods:FavEntity)

    @Query("SELECT * FROM productsfav")
    fun getAllOFavProd():Flow<MutableList<FavEntity>>

    @Delete
    suspend fun deleteFavProds(prods:FavEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(orderEntity: OrderEntity)

    @Query("SELECT * FROM orders")
    fun getAllOrders():Flow<MutableList<OrderEntity>>

    @Query("DELETE FROM orders")
    suspend fun deleteAllOrders()

    @Delete
    suspend fun deleteOrder(orderEntity: OrderEntity)

    @Update
    suspend fun UpdateOrder(orderEntity: OrderEntity)

}