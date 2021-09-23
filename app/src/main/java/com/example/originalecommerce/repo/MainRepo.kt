package com.example.originalecommerce.repo

import com.example.originalecommerce.data.remote.FireBasecSource
import javax.inject.Inject


class MainRepo @Inject constructor(
        var fireBasecSource: FireBasecSource
) {
}