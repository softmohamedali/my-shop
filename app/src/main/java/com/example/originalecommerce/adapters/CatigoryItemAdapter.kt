package com.example.originalecommerce.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.originalecommerce.databinding.LayoutCatigoryItemBinding
import com.example.originalecommerce.databinding.LayoutProductItemBinding
import com.example.originalecommerce.models.Product
import com.example.originalecommerce.ui.body.MainBodyFragmentDirections
import com.example.orignal_ecommerce_manger.models.Catigory
import com.example.orignal_ecommerce_manger.util.MyDiff
import com.squareup.picasso.Picasso

class CatigoryItemAdapter:RecyclerView.Adapter<CatigoryItemAdapter.VH>() {
    private var catigoryList= mutableListOf<Catigory>()

    class VH(var view:LayoutCatigoryItemBinding):RecyclerView.ViewHolder(view.root){
        companion object{
            fun from(parent: ViewGroup): CatigoryItemAdapter.VH
            {
                val bindingview= LayoutCatigoryItemBinding.inflate(LayoutInflater.from(parent.context))
                return CatigoryItemAdapter.VH(bindingview)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatigoryItemAdapter.VH {
        return VH.from(parent)

    }

    override fun onBindViewHolder(holder: CatigoryItemAdapter.VH, position: Int) {
        val cat=catigoryList[position]
        val view=holder.view
        Picasso.get().load(cat.img)
            .into(view.imgCatiitemlay)
        view.tvNameCatitem.text=cat.name

        holder.itemView.setOnClickListener {
            val action=MainBodyFragmentDirections.actionMainBodyFragmentToProductCatigoryFragment(cat.name)
            it.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return catigoryList.size
    }

    fun setData(newCatigores:MutableList<Catigory>)
    {
        val mydiff= MyDiff(catigoryList,newCatigores)
        val result= DiffUtil.calculateDiff(mydiff)
        catigoryList=newCatigores
        result.dispatchUpdatesTo(this)
    }
}