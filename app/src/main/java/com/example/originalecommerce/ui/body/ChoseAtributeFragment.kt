package com.example.originalecommerce.ui.body

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.originalecommerce.R
import com.example.originalecommerce.adapters.AdapterChoseColor
import com.example.originalecommerce.adapters.AdapterChoseSize
import com.example.originalecommerce.adapters.AdapterColorItem
import com.example.originalecommerce.data.local.entitys.OrderEntity
import com.example.originalecommerce.databinding.FragmentChoseAtributeBinding
import com.example.originalecommerce.models.Product
import com.example.originalecommerce.viewmodels.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChoseAtributeFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentChoseAtributeBinding?=null
    private val binding get() = _binding!!

    private val navArgs by navArgs<ChoseAtributeFragmentArgs>()

    private val mMainViewModel by viewModels<MainViewModel>()

    private lateinit var product: Product

    private val sizeList= mutableListOf<String>()
    private lateinit var colorList:MutableList<String>

    private val madapterChoseColor by lazy { AdapterChoseColor() }
    private val madapterChoseSize by lazy { AdapterChoseSize() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentChoseAtributeBinding.inflate(layoutInflater)
        setUpView()
        return binding.root
    }

    private fun setUpView()
    {
        setRecy()
        product=navArgs.prod
        if (product.colorAvialable!=null)
        {
            colorList=product.colorAvialable!!
            colorList.forEach {
                madapterChoseColor.addItem(it)
            }
        }
        if (product.sizeAvilable!=null)
        {
            product.sizeAvilable?.split(",")?.forEach {
                sizeList.add(it)
            }
            sizeList.forEach {
                madapterChoseSize.addItem(it)
            }

        }

        binding.btnComplete.setOnClickListener {
            val size=madapterChoseSize.checkSize
            val color=madapterChoseColor.cheackColor
            if (size.isNullOrEmpty())
            {
                Toast.makeText(requireActivity(),"Please Cheack Size",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (color.isNullOrEmpty())
            {
                Toast.makeText(requireActivity(),"Please Cheack Color",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val orders=OrderEntity(
                product,
                1,"",
                product.buyPrice.toString().toFloat(),
                product.buyPrice.toString().toFloat(),
                color,
                size
            )
            mMainViewModel.insertOrder(orders)
            showToastSuc()
            findNavController().popBackStack()
        }

    }

    private fun showToastSuc()
    {
        val toastView=LayoutInflater.from(requireActivity()).inflate(R.layout.layout_success_toast,null,false)
        val myToast=Toast(requireActivity())
        myToast.view=toastView
        myToast.duration=Toast.LENGTH_SHORT
        myToast.setGravity(Gravity.CENTER_VERTICAL,0,0)
        myToast.show()
    }

    private fun setRecy() {
        binding.recyChosecolor.apply {
            adapter=madapterChoseColor
            layoutManager=LinearLayoutManager(requireActivity(),RecyclerView.HORIZONTAL,false)
        }
        binding.recyChosesize.apply {
            adapter=madapterChoseSize
            layoutManager=LinearLayoutManager(requireActivity(),RecyclerView.HORIZONTAL,false)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}