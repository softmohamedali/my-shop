package com.example.orignal_ecommerce_manger.util

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar

fun <T> LiveData<T>.observerOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>)
{
    observe(lifecycleOwner,object : Observer<T> {
        override fun onChanged(t: T) {
            removeObserver(this)
            observer.onChanged(t)
        }
    })
}

fun myToast(conntext: Context, msg: String?){
    Toast.makeText(conntext,msg, Toast.LENGTH_SHORT).show()
}

fun mySnack(context: Context, view: View,msg:String){
    Snackbar.make(context,view,msg,Snackbar.LENGTH_SHORT)
        .setAction("ok",{})
        .show()
}