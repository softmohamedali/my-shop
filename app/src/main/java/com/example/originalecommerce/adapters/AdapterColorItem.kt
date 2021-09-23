package com.example.originalecommerce.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.originalecommerce.R


class AdapterColorItem:RecyclerView.Adapter<AdapterColorItem.Vh>() {
    private val colorsList= mutableListOf<String>()
    class Vh(var view: View):RecyclerView.ViewHolder(view)
    {
        companion object{
            fun from(parent: ViewGroup):AdapterColorItem.Vh
            {
                val view=LayoutInflater.from(parent.context).inflate(R.layout.layout_dot_color_item,parent,false)
                return Vh(view)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh.from(parent)
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        val color=colorsList[position]
        holder.view.setBackgroundColor(Color.parseColor(color))

    }

    override fun getItemCount(): Int {
      return colorsList.size
    }

    fun addItem(col:String)
    {
        colorsList.add(col)
        notifyItemInserted(colorsList.size-1)
    }
}

















