package com.example.originalecommerce.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.originalecommerce.R
import com.example.originalecommerce.databinding.LayoutChoseColorItemBinding

class AdapterChoseColor : RecyclerView.Adapter<AdapterChoseColor.Vh>() {
    private val colorsList = mutableListOf<String>()

    private val holders= mutableListOf<AdapterChoseColor.Vh>()
    private val holdersPos= mutableListOf<Int>()

    var cheackColor:String?=null

    class Vh(var view: LayoutChoseColorItemBinding) : RecyclerView.ViewHolder(view.root) {
        companion object {
            fun from(parent: ViewGroup): AdapterChoseColor.Vh {
                val view = LayoutChoseColorItemBinding.inflate(LayoutInflater.from(parent.context))
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
        val color = colorsList[position]
        holder.view.colorColorItemRecy.setBackgroundColor(Color.parseColor(color))
        holder.view.cbColorItemRecy.setOnClickListener {
            if (!holder.view.cbColorItemRecy.isChecked)
            {
                cheackColor=null
                holder.view.cbColorItemRecy.isChecked=true

            }
            for (i in holdersPos)
            {
                if (i!=position)
                {
                    holders[i].view.cbColorItemRecy.isChecked=false

                }
            }
            cheackColor=color
        }

    }

    override fun getItemCount(): Int {
        return colorsList.size
    }


    fun addItem(col: String) {
        colorsList.add(col)
        notifyItemInserted(colorsList.size - 1)
    }
}