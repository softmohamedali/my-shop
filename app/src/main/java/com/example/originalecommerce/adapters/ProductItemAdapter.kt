package com.example.originalecommerce.adapters

import android.util.Log
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
import com.example.originalecommerce.databinding.LayoutProductItemBinding
import com.example.originalecommerce.models.Product
import com.example.originalecommerce.ui.body.CatigoresFragmentDirections
import com.example.originalecommerce.ui.body.MainBodyFragmentDirections
import com.example.originalecommerce.ui.body.ProductCatigoryFragmentDirections
import com.example.originalecommerce.ui.body.SearchFragmentDirections
import com.example.originalecommerce.utils.Constants
import com.example.originalecommerce.viewmodels.MainViewModel
import com.example.orignal_ecommerce_manger.util.MyDiff

class ProductItemAdapter(
    var myViewModel:MainViewModel,
    var lifeCycle:LifecycleOwner,
    var container:String,
):RecyclerView.Adapter<ProductItemAdapter.Vh>() {
    private var productsList= mutableListOf<Product>()

    class Vh(var view:LayoutProductItemBinding):RecyclerView.ViewHolder(view.root)
    {
        companion object{
            fun from(parent: ViewGroup):Vh
            {
                val bindingview=LayoutProductItemBinding.inflate(LayoutInflater.from(parent.context))
                return Vh(bindingview)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductItemAdapter.Vh {
        return Vh.from(parent)
    }

    override fun onBindViewHolder(holder: ProductItemAdapter.Vh, position: Int) {
        val product=productsList[position]
        val view=holder.view
        view.etName.text=product.name
        view.etPrice.text=product.buyPrice
        view.etOffer.text="$ ${product.offer}"
        view.imgImg.load(product.img){
            error(R.drawable.ic_image)
            crossfade(600)
        }
        view.rbRating.rating=product.Rating.toString().toFloat()

        holder.itemView.setOnClickListener {
            if (container==Constants.MAIN_CONTAINER)
            {
                val action=MainBodyFragmentDirections.
                actionMainBodyFragmentToShowPeoductFragment(product)
                it.findNavController().navigate(action)
            }
            if (container==Constants.PRODCAT_CONTAINER)
            {
                val action=ProductCatigoryFragmentDirections.
                actionProductCatigoryFragmentToShowPeoductFragment(product)
                it.findNavController().navigate(action)
            }
            if (container==Constants.SEARCH_CONTAINER)
            {
                val action=SearchFragmentDirections.
                actionSearchFragmentToShowPeoductFragment(product)
                it.findNavController().navigate(action)
            }
            if (container==Constants.CATIGORES_CONTAINER)
            {
                val action=CatigoresFragmentDirections.
                actionCatigoresFragmentToShowPeoductFragment(product)
                it.findNavController().navigate(action)
            }
        }



    }

    override fun getItemCount(): Int {
        return productsList.size
    }


    fun setData(newProducts:MutableList<Product>)
    {
        val mydiff=MyDiff(productsList,newProducts)
        val result=DiffUtil.calculateDiff(mydiff)
        productsList=newProducts
        result.dispatchUpdatesTo(this)
    }


}

