package com.example.originalecommerce.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.originalecommerce.databinding.LayoutChoseSizeItemBinding

class AdapterChoseSize : RecyclerView.Adapter<AdapterChoseSize.Vh>() {
    private val sizeList = mutableListOf<String>()

    private val holders= mutableListOf<AdapterChoseSize.Vh>()
    private val holdersPos= mutableListOf<Int>()

    var checkSize:String?=null

    class Vh(var view: LayoutChoseSizeItemBinding) : RecyclerView.ViewHolder(view.root) {
        companion object {
            fun from(parent: ViewGroup): AdapterChoseSize.Vh {
                val view = LayoutChoseSizeItemBinding.inflate(LayoutInflater.from(parent.context))
                return Vh(view)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh.from(parent)
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holders.add(holder)
        holdersPos.add(position)
        val size = sizeList[position]
        holder.view.cbSizeItemRecy.text=size
        holder.view.cbSizeItemRecy.setOnClickListener {
            for (i in holdersPos)
            {
                if (i!=position)
                {
                    holders[i].view.cbSizeItemRecy.isChecked=false

                }
            }
            checkSize=size
        }

    }

    override fun getItemCount(): Int {
        return sizeList.size
    }

    fun addItem(size: String) {
        sizeList.add(size)
        notifyItemInserted(sizeList.size - 1)
    }
}