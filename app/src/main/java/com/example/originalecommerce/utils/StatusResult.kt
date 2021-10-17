package com.example.orignal_ecommerce_manger.util

sealed class StatusResult <T>(
    var data:T?=null,
    var masg:String?=null
)
{
    class Success <T>(data: T?=null):StatusResult<T>(data)
    class Error<T>(masg: String?,data: T?=null):StatusResult<T>(data,masg)
    class Loading<T>(data: T?=null):StatusResult<T>(data)
}