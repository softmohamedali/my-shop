package com.example.originalecommerce.repo

import com.example.originalecommerce.data.local.Dao
import com.example.originalecommerce.data.local.DataBase
import com.example.originalecommerce.data.remote.FireBasecSource
import javax.inject.Inject


class MainRepo @Inject constructor(
        var fireBasecSource: FireBasecSource,
        var dataBaseSource:DataBase
) {
}