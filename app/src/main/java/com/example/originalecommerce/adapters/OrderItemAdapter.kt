package com.example.originalecommerce.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.originalecommerce.R
import com.example.originalecommerce.data.local.entitys.OrderEntity
import com.example.originalecommerce.databinding.LayoutOrderItemBinding
import com.example.originalecommerce.ui.body.MyCartFragmentDirections
import com.example.originalecommerce.viewmodels.MainViewModel
import com.example.orignal_ecommerce_manger.util.MyDiff

class OrderItemAdapter (var myViewModel: MainViewModel, var lifeCycle: LifecycleOwner)
    : RecyclerView.Adapter<OrderItemAdapter.Vh>() {
    private var orderEntityList= mutableListOf<OrderEntity>()

    val totalPrice = MutableLiveData<Float>()
    private var _totoal=0f
    init {
        totalPrice.value=0f
    }

    class Vh(var view: LayoutOrderItemBinding): RecyclerView.ViewHolder(view.root)
    {
        companion object{
            fun from(parent: ViewGroup):Vh
            {
                val bindingview= LayoutOrderItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                return Vh(bindingview)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItemAdapter.Vh {
        return Vh.from(parent)
    }

    override fun onBindViewHolder(holder: OrderItemAdapter.Vh, position: Int) {
        val orderEntity=orderEntityList[position]
        val order=orderEntity.prodct
        val view=holder.view
        _totoal+= orderEntity.totalPrice!!
        totalPrice.value=_totoal
        view.tvCount.text=orderEntity.count.toString()
        view.tvNameOrderitem.text=order?.name
        view.tvPriceOrderitem.text=order?.buyPrice
        view.imgOrderItem.load(order?.img){
            error(R.drawable.ic_image)
            crossfade(600)
        }

        holder.view.btnDeleteOrderitem.setOnClickListener {
            myViewModel.deleteOrder(orderEntityList[position])
            _totoal-=orderEntity.totalPrice!!
            totalPrice.value=_totoal
            deleteItem(position)

        }

        holder.view.btnPlusOrderitem.setOnClickListener {
            val count=((view.tvCount.text.toString().toInt())+1).toString()
            view.tvCount.setText(count)
            _totoal+=orderEntity.price!!
            totalPrice.value=_totoal
            orderEntity.count=view.tvCount.text.toString().toInt()
            orderEntity.totalPrice=orderEntity.price!!*orderEntity.count!!
            myViewModel.updateOrder(orderEntity)
        }

        holder.view.btnMinOrderitem.setOnClickListener {
            if ((view.tvCount.text.toString().toInt())-1<1)
            {
                view.tvCount.setText("1")
                orderEntity.count=1
                orderEntity.totalPrice=orderEntity.price
                myViewModel.updateOrder(orderEntity)
            }else{
                val count=((view.tvCount.text.toString().toInt())-1).toString()
                view.tvCount.setText(count.toString())
                _totoal-=orderEntity.price!!
                totalPrice.value=_totoal
                orderEntity.count=view.tvCount.text.toString().toInt()
                orderEntity.totalPrice=orderEntity.price!!*orderEntity.count!!
                myViewModel.updateOrder(orderEntity)

            }
        }

    }

    override fun getItemCount(): Int {
        return orderEntityList.size
    }


    fun setData(newOrder:MutableList<OrderEntity>)
    {
        val mydiff= MyDiff(orderEntityList,newOrder)
        val result= DiffUtil.calculateDiff(mydiff)
        orderEntityList=newOrder
        result.dispatchUpdatesTo(this)
    }

    fun deleteItem(pos:Int)
    {
        orderEntityList.removeAt(pos)
        notifyItemChanged(pos)
    }
    fun deleAll()
    {
        orderEntityList= mutableListOf<OrderEntity>()
        myViewModel.deleteAllOrder()
        notifyDataSetChanged()
        totalPrice.value=0f
    }


}