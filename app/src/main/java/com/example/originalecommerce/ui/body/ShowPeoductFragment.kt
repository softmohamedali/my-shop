package com.example.originalecommerce.ui.body

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.originalecommerce.R
import com.example.originalecommerce.adapters.AdapterColorItem
import com.example.originalecommerce.data.local.entitys.FavEntity
import com.example.originalecommerce.databinding.FragmentShowPeoductBinding
import com.example.originalecommerce.databinding.FragmentSplachBinding
import com.example.originalecommerce.models.Product
import com.example.originalecommerce.utils.Constants
import com.example.originalecommerce.viewmodels.MainViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowPeoductFragment : Fragment() {
    private var _binding: FragmentShowPeoductBinding?=null
    private val binding get() = _binding!!

    private var isFav=false
    private var favid:Int?=null
    private val navArgs by navArgs<ShowPeoductFragmentArgs>()

    private lateinit var product:Product

    private val mColorAdapter by lazy { AdapterColorItem() }

    private val mainViewModel by viewModels<MainViewModel>()

    private val myBundle by lazy { Bundle() }
    val json= Gson()

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
            val action=ShowPeoductFragmentDirections.actionShowPeoductFragmentToChoseAtributeFragment(product)
            findNavController().navigate(action)
        }
        binding.btnOrdersShprofrag.setOnClickListener {
            findNavController().navigate(R.id.action_showPeoductFragment_to_myCartFragment)
        }
        product.colorAvialable?.forEach {
            mColorAdapter.addItem(it)
        }
        binding.tvNameShprofrag.text=product.name
        binding.tvDiscriptionShprofrag.text=product.description
        binding.tvOfferShprofrag.text="- ${product.offer} %"
        binding.tvSalepriceShprofrag.text="${product.salePrice} $"
        binding.tvRatingShprodfrag.text="${product.Rating} / 5"
        binding.rbRatingShprofrag.rating=product.Rating!!.toFloat()
        binding.tvSizeavilableShowfrag.text=product.sizeAvilable

        binding.imgShprofrag.load(product.img){
            error(R.drawable.ic_image)
        }

        mainViewModel.readFavEntity.observe(viewLifecycleOwner,{
            it.forEach {
                if (it.prod.hashCode() == product.hashCode()) {
                    changeHertColor(binding.btnOrdersShprofrag, R.color.myred)
                    isFav=true
                    favid=it.id!!
                } else {
                    changeHertColor(binding.btnOrdersShprofrag, R.color.darkgray)
                    isFav=false
                    favid=null
                }
            }
        })

        binding.btnOrdersShprofrag.setOnClickListener {
            if (isFav)
            {
                changeHertColor(binding.btnOrdersShprofrag,R.color.darkgray)
                mainViewModel.deleteFav(FavEntity(product,favid))
                isFav=false

            }else{
                changeHertColor(binding.btnOrdersShprofrag,R.color.myred)
                mainViewModel.insertFav(FavEntity(product))
                isFav=true
            }
        }

    }

    fun setUpRecy()
    {
        binding.recyColorvilableShowfrag.apply {
            adapter=mColorAdapter
            layoutManager=LinearLayoutManager(requireActivity(),RecyclerView.HORIZONTAL,false)
        }
    }

    private fun changeHertColor(view: ImageButton, color:Int)
    {
        view.setColorFilter(ContextCompat.getColor(view.context,color))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}