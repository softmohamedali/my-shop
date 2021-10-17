package com.example.originalecommerce.ui.body

import android.content.Context
import android.icu.util.Calendar
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.originalecommerce.R
import com.example.originalecommerce.adapters.OrderItemAdapter
import com.example.originalecommerce.data.local.entitys.OrderEntity
import com.example.originalecommerce.databinding.FragmentMycartBinding
import com.example.originalecommerce.models.Paymnts
import com.example.originalecommerce.utils.Constants
import com.example.originalecommerce.viewmodels.MainViewModel
import com.example.orignal_ecommerce_manger.util.StatusResult
import com.example.orignal_ecommerce_manger.util.observerOnce
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MyCartFragment : Fragment() {
    private var _binding: FragmentMycartBinding?=null
    private val binding get() = _binding!!

    private val mMainViewModel by viewModels<MainViewModel>()
    private val mOrderadapter by lazy { OrderItemAdapter(mMainViewModel,requireActivity()) }


    @Inject
    lateinit var dataStore: DataStore<Preferences>

    private lateinit var orders:MutableList<OrderEntity>


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding=FragmentMycartBinding.inflate(layoutInflater)
        binding.tvTotalpriceMycart.setText("0")
        setUpRecy()

        binding.btnBackMycart.setOnClickListener{
            findNavController().popBackStack()
        }
        mOrderadapter.totalPrice.observe(viewLifecycleOwner,{
            if (it==0f)
            {
                showEmptyorder(true)
            }else{
                showEmptyorder(false)
            }
            binding.tvTotalpriceMycart.setText(it.toString())
        })

        mMainViewModel.readOrders.observerOnce(viewLifecycleOwner,{
            binding.tvTotalpriceMycart.setText("0")
            if (it.isEmpty())
            {
                mOrderadapter.setData(it)
                showEmptyorder(true)
            }else {
                orders=it
                mOrderadapter.setData(it)
            }
        })
        binding.btnBuynowMycart.setOnClickListener {
            cheakWhichNav(orders)
        }


        mMainViewModel.paymntsIsUpload.observe(viewLifecycleOwner,{
            when {
                it is StatusResult.Success -> {
                    binding.prPaymentsMycart.visibility=View.INVISIBLE
                    showToast()
                }
                it is StatusResult.Error -> {
                    binding.prPaymentsMycart.visibility=View.INVISIBLE
                    Toast.makeText(requireActivity(),"${it.masg}",Toast.LENGTH_SHORT).show()
                }
                it is StatusResult.Loading -> {
                    binding.prPaymentsMycart.visibility=View.VISIBLE
                }
            }
        })
        return binding.root
    }

    private fun cheakWhichNav(order: MutableList<OrderEntity>) {
        if (binding.tvTotalpriceMycart.text=="0")
        {
            Toast.makeText(requireActivity(),"No Orders yet",Toast.LENGTH_SHORT).show()
        }
        else if (!cheakPayMethod())
        {
            findNavController().navigate(R.id.action_myCartFragment_to_paymentMethodFragment)
        }
        else if (!cheakinfoOrder())
        {
            findNavController().navigate(R.id.action_myCartFragment_to_orderInfoFragment)
        }else{
            donePayments(order)
        }
    }

    private fun donePayments(order: MutableList<OrderEntity>) {
        var prefrences: Preferences?=null
        lifecycleScope.launch {
            prefrences=dataStore.data.first()
        }
        val florNum= prefrences?.get(stringPreferencesKey(Constants.FLOOR_NUM_SP))
        val fNAme= prefrences?.get(stringPreferencesKey(Constants.FIRST_NAME_SP))
        val lName= prefrences?.get(stringPreferencesKey(Constants.LAST_NAME_SP))
        val marker= prefrences?.get(stringPreferencesKey(Constants.MARKER_SP))
        val place= prefrences?.get(stringPreferencesKey(Constants.PLACE_TYPE_SP))
        val lang= prefrences?.get(stringPreferencesKey(Constants.LANG_SP))
        val lat= prefrences?.get(stringPreferencesKey(Constants.LAT_SP))
        val nameOrNum= prefrences?.get(stringPreferencesKey(Constants.NAMEORNUM_BUILD_SP))
        val phone= prefrences?.get(stringPreferencesKey(Constants.PHONE_SP))
        val note= prefrences?.get(stringPreferencesKey(Constants.NOTE_SP))
        val time:String=Calendar.getInstance().time.toString()

        val fullLocation="lat:${lat}    lang:${lang}     place:${place}     marker:${marker}    floorNum:${florNum}" +
                "    name or num of building:${nameOrNum}" +
                "    note:${note}"
        val fullnamem=fNAme+lName
        val paymnts=Paymnts(order,fullnamem,phone!!,fullLocation,time,1)
        mMainViewModel.uploadPayments(paymnts)
        mOrderadapter.deleAll()
    }


    private fun setUpRecy()
    {
        binding.recyOrderMycart.apply {
            adapter=mOrderadapter
            layoutManager=LinearLayoutManager(requireActivity(),RecyclerView.VERTICAL,false)
        }
    }

    fun showEmptyorder(show:Boolean)
    {
        if (show)
        {
            binding.imgErrorMycart.visibility=View.VISIBLE
            binding.tvErrorMycart.visibility=View.VISIBLE
        }else{
            binding.imgErrorMycart.visibility=View.INVISIBLE
            binding.tvErrorMycart.visibility=View.INVISIBLE
        }
    }

    private fun cheakPayMethod():Boolean
    {
        var prefrences: Preferences?=null
        lifecycleScope.launch {
            prefrences=dataStore.data.first()
        }
        val cvv= prefrences?.get(stringPreferencesKey(Constants.CVV2_SP))
        return cvv!=null
    }

    private fun cheakinfoOrder():Boolean
    {
        var prefrences: Preferences?=null
        lifecycleScope.launch {
            prefrences=dataStore.data.first()
        }
        val info= prefrences?.get(stringPreferencesKey(Constants.FLOOR_NUM_SP))
        return info!=null
    }

    private fun showToast()
    {
        val toast=Toast(requireActivity())
        toast.duration=Toast.LENGTH_SHORT
        toast.setGravity(Gravity.CENTER,0,0)
        toast.view=LayoutInflater.from(requireActivity()).inflate(R.layout.layout_succes,null,false)
        toast.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}