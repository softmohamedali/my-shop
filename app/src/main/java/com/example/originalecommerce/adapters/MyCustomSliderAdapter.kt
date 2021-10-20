package com.example.originalecommerce.adapters

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import coil.load
import com.example.originalecommerce.R
import com.example.originalecommerce.models.Product
import com.opensooq.pluto.base.PlutoAdapter
import com.opensooq.pluto.base.PlutoViewHolder
import com.opensooq.pluto.listeners.OnItemClickListener

class MyCustomSliderAdapter (items: MutableList<Product> , onItemClickListener: OnItemClickListener<Product>? = null)
    : PlutoAdapter<Product, MyCustomSliderAdapter.YourViewHolder>(items,onItemClickListener) {

    override fun getViewHolder(parent: ViewGroup, viewType: Int): YourViewHolder {
        return YourViewHolder(parent, R.layout.layout_slider_view)
    }

    class YourViewHolder(parent: ViewGroup, itemLayoutId: Int) : PlutoViewHolder<Product>(parent, itemLayoutId) {
        private var img: ImageView = getView(R.id.slider_img)
        private var tv: TextView = getView(R.id.tv_slider)
        override fun set(item: Product, position: Int) {
            img.load(item.img)
            tv.text ="Offer Reash to - ${item.offer}%"
        }

//        override fun set(item: YourModel, position: Int) {
//            //  yourImageLoader.with(mContext).load(item.getPosterId()).into(ivPoster);
//            tvRating.text = item.imdbRating
//        }
    }
}