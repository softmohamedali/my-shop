package com.example.originalecommerce.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.originalecommerce.data.local.entitys.FavEntity
import com.example.originalecommerce.data.local.entitys.NotificationEntity
import com.example.originalecommerce.databinding.LayoutChoseColorItemBinding
import com.example.originalecommerce.databinding.LayoutNotificationItemBinding
import com.example.orignal_ecommerce_manger.util.MyDiff

class NotifiAdapter: RecyclerView.Adapter<NotifiAdapter.Vh>() {
    private var notiList = mutableListOf<NotificationEntity>()


    class Vh(var view: LayoutNotificationItemBinding) : RecyclerView.ViewHolder(view.root) {
        companion object {
            fun from(parent: ViewGroup): NotifiAdapter.Vh {
                val view = LayoutNotificationItemBinding.inflate(LayoutInflater.from(parent.context))
                return Vh(view)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh.from(parent)
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.view.tvMsg.text=notiList[position].msg
        holder.view.tvTime.text=notiList[position].time
    }

    override fun getItemCount(): Int {
        return notiList.size
    }


    fun setData(newNotif:MutableList<NotificationEntity>)
    {
        val mydiff= MyDiff(notiList,newNotif)
        val result= DiffUtil.calculateDiff(mydiff)
        notiList=newNotif
        result.dispatchUpdatesTo(this)
    }

}