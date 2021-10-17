package com.example.originalecommerce.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.originalecommerce.R
import com.example.originalecommerce.data.local.entitys.FavEntity
import com.example.originalecommerce.databinding.LayoutFavItemBinding
import com.example.originalecommerce.ui.body.FavFragmentDirections
import com.example.originalecommerce.ui.body.MainBodyFragmentDirections
import com.example.originalecommerce.viewmodels.MainViewModel
import com.example.orignal_ecommerce_manger.util.MyDiff

class FavItemAdapter (var myViewModel: MainViewModel, var lifeCycle: LifecycleOwner)
    : RecyclerView.Adapter<FavItemAdapter.Vh>() {
    private var favEntityList= mutableListOf<FavEntity>()

    class Vh(var view: LayoutFavItemBinding): RecyclerView.ViewHolder(view.root)
    {
        companion object{
            fun from(parent: ViewGroup):Vh
            {
                val bindingview= LayoutFavItemBinding.inflate(LayoutInflater.from(parent.context))
                return Vh(bindingview)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavItemAdapter.Vh {
        return Vh.from(parent)
    }

    override fun onBindViewHolder(holder: FavItemAdapter.Vh, position: Int) {
        val fav=favEntityList[position].prod
        val view=holder.view
        var favid:Int?=null
        changeHertColor(view.imgFavItem,R.color.myred)
        view.tvNameItem.text=fav.name
        view.tvPriceItem.text=fav.buyPrice
        view.imgImggItem.load(fav.img){
            error(R.drawable.ic_image)
            crossfade(600)
        }
        view.rbFavItem.rating=fav.Rating.toString().toFloat()

        holder.itemView.setOnClickListener {
            val action= FavFragmentDirections.actionFavFragmentToShowPeoductFragment(fav)
            it.findNavController().navigate(action)
        }

        myViewModel.readFavEntity.observe(lifeCycle,{
            it.forEach {
                if (it.prod.hashCode() == fav.hashCode()) {
                    favid=it.id
                }
            }
        })


        holder.view.imgFavItem.setOnClickListener {
            myViewModel.deleteFav(FavEntity(fav, favid))
        }


    }


    override fun getItemCount(): Int {
        return favEntityList.size
    }


    fun setData(newFAvs:MutableList<FavEntity>)
    {
        val mydiff= MyDiff(favEntityList,newFAvs)
        val result= DiffUtil.calculateDiff(mydiff)
        favEntityList=newFAvs
        result.dispatchUpdatesTo(this)
    }

    private fun changeHertColor(view: ImageButton, color:Int)
    {
        view.setColorFilter(ContextCompat.getColor(view.context,color))
    }
}