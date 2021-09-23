package com.example.originalecommerce.ui.body

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.originalecommerce.R
import com.example.originalecommerce.adapters.AdapterColorItem
import com.example.originalecommerce.databinding.FragmentShowPeoductBinding
import com.example.originalecommerce.databinding.FragmentSplachBinding
import com.example.originalecommerce.models.Product

class ShowPeoductFragment : Fragment() {
    private var _binding: FragmentShowPeoductBinding?=null
    private val binding get() = _binding!!

    private val navArgs by navArgs<ShowPeoductFragmentArgs>()

    private lateinit var product:Product

    private val mColorAdapter by lazy { AdapterColorItem() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentShowPeoductBinding.inflate(layoutInflater)
        setUpView()
        return binding.root
    }

    private fun setUpView()
    {
        setUpRecy()
        product=navArgs.product
        binding.btnBackShprofrag.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnAddtocartShprofrag.setOnClickListener {

            findNavController().navigate(R.id.action_showPeoductFragment_to_choseAtributeFragment)
        }
        binding.btnOrdersShprofrag.setOnClickListener {

        }
        product.colorAvialable?.forEach {
            mColorAdapter.addItem(it)
        }
        binding.tvNameShprofrag.text=product.name
        binding.tvDiscriptionShprofrag.text=product.description
        binding.tvOfferShprofrag.text="- ${product.offer} %"
        binding.tvSalepriceShprofrag.text=product.salePrice
        binding.tvRatingShprodfrag.text="${product.Rating} / 5"
        binding.rbRatingShprofrag.rating=product.Rating!!.toFloat()
        binding.tvSizeavilableShowfrag.text=product.sizeAvilable

        binding.imgShprofrag.load(product.img){
            error(R.drawable.ic_image)
        }
    }

    fun setUpRecy()
    {
        binding.recyColorvilableShowfrag.apply {
            adapter=mColorAdapter
            layoutManager=LinearLayoutManager(requireActivity(),RecyclerView.HORIZONTAL,false)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}