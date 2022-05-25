package com.example.originalecommerce.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.androiddevs.shoppinglisttestingyt.getOrAwaitValue
import com.example.originalecommerce.data.local.entitys.OrderEntity
import com.example.originalecommerce.models.Product
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class DaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    private lateinit var dataBase: DataBase
    private lateinit var dao: Dao

    @Before
    fun setUp()
    {
        dataBase=Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            DataBase::class.java
        ).allowMainThreadQueries().build()
        dao=dataBase.getDao()
    }

    @After
    fun setDown()
    {
        dataBase.close()
    }

    @Test
    fun testInseartion()= runBlockingTest {
            val order=OrderEntity(Product(),3,"",4f,4f,"","",41)
            dao.insertOrder(order)

            val orders=dao.getAllOrders().asLiveData().getOrAwaitValue ()
            assertThat(orders).contains(order)
    }
}