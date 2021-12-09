package com.example.originalecommerce.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.originalecommerce.R
import com.example.originalecommerce.data.local.entitys.OrderEntity
import com.example.originalecommerce.databinding.LayoutRequestItemBinding
import com.example.orignal_ecommerce_manger.util.MyDiff

class RequestItemAdapter (
): RecyclerView.Adapter<RequestItemAdapter.Vh>() {
    private var requestesList= mutableListOf<OrderEntity>()

    class Vh(var view: LayoutRequestItemBinding): RecyclerView.ViewHolder(view.root)
    {
        companion object{
            fun from(parent: ViewGroup):Vh
            {
                val bindingview= LayoutRequestItemBinding.inflate(LayoutInflater.from(parent.context))
                return Vh(bindingview)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestItemAdapter.Vh {
        return Vh.from(parent)
    }

    override fun onBindViewHolder(holder: RequestItemAdapter.Vh, position: Int) {
        val request=requestesList[position]
        val view=holder.view

        view.etNameRequestitem.text=request.prodct?.name
        view.etPriceRequestitem.text="price :${request.prodct?.buyPrice}"
        view.etColorRequestitem.text="color :"
        view.etCountRequestitem.text="count: ${request.count}"
        view.colorDotRequestitem.setBackgroundColor(Color.parseColor(request.colors!!))
        view.etSizeRequestitem.text="size :${request.sizeprod}"
        view.imgImgRequestitem.load(request.prodct?.img){
            error(R.drawable.ic_image)
            crossfade(600)
        }



    }

    override fun getItemCount(): Int {
        return requestesList.size
    }


    fun setData(newRequests:MutableList<OrderEntity>)
    {
        val mydiff= MyDiff(requestesList,newRequests)
        val result= DiffUtil.calculateDiff(mydiff)
        requestesList=newRequests
        result.dispatchUpdatesTo(this)
    }


}
