package com.example.originalecommerce.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.originalecommerce.data.local.entitys.OrderEntity
import com.example.originalecommerce.databinding.LayoutShowOrderItemBinding
import com.example.originalecommerce.models.Paymnts
import com.example.originalecommerce.models.Product
import com.example.orignal_ecommerce_manger.util.MyDiff
import com.kofigyan.stateprogressbar.StateProgressBar

class TrackOrderAdapter(
    trackItemListener: TrackItemListener
) : RecyclerView.Adapter<TrackOrderAdapter.Vh>() {
    private var paymentsList= mutableListOf<Paymnts>()
    private var trackListen=trackItemListener
    class Vh(var view: LayoutShowOrderItemBinding): RecyclerView.ViewHolder(view.root)
    {
        companion object{
            fun from(parent: ViewGroup):Vh
            {
                val bindingview= LayoutShowOrderItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                return Vh(bindingview)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackOrderAdapter.Vh {
        return Vh.from(parent)
    }

    override fun onBindViewHolder(holder: TrackOrderAdapter.Vh, position: Int) {
        val myView=holder.view
        val myPay=paymentsList[position]
        var totalPrice=0f
        val statusDes= arrayOf("request\nis done","in\nshipping","on the\nway"," delivered\nhanded")
        myView.progress3.setStateDescriptionData(statusDes)
        setStateNumber(myPay.statusPayments!!,myView.progress3)
        var mcount=0
        myPay.orders?.forEach {
            mcount+= it.count!!
        }
        myView.tvCountorder.text="Count Of Orders : ${mcount}"
        myView.tvTimeOrdertrack.text="Time Of Oreder : ${myPay.time}"
        myPay.orders?.forEach {
            totalPrice+=it.totalPrice!!
        }
        myView.tvTotalpriceOrdertrack.text="Total Price : $totalPrice"
        holder.itemView.setOnClickListener {
            trackListen.onItemClik(myPay.orders!!)
        }

    }



    override fun getItemCount(): Int {
        return paymentsList.size
    }


    fun setData(newPayments:MutableList<Paymnts>)
    {
        val mydiff= MyDiff(paymentsList,newPayments)
        val result= DiffUtil.calculateDiff(mydiff)
        paymentsList=newPayments
        result.dispatchUpdatesTo(this)
    }

    private fun setStateNumber(sta:Int, progress3: StateProgressBar) {
        when(sta){
            1->{progress3.setCurrentStateNumber(StateProgressBar.StateNumber.ONE)}
            2->{progress3.setCurrentStateNumber(StateProgressBar.StateNumber.TWO)}
            3->{progress3.setCurrentStateNumber(StateProgressBar.StateNumber.THREE)}
            4->{progress3.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR)}
            else->{progress3.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR)}
        }
    }

    interface TrackItemListener{
        fun onItemClik(
            items: MutableList<OrderEntity>,
        )
    }


}
