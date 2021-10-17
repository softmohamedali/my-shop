package com.example.originalecommerce.di

import android.content.Context
import androidx.room.Room
import com.example.originalecommerce.data.local.DataBase
import com.example.originalecommerce.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context)=
            Room.databaseBuilder(
                    context,
                    DataBase::class.java,
                    Constants.DATABASE_NAME
            )
                .fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideDao(dataBase: DataBase)=
            dataBase.getDao()

}