package com.example.originalecommerce.ui.body

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.originalecommerce.adapters.ProductItemAdapter
import com.example.originalecommerce.databinding.FragmentProductCatigoryBinding
import com.example.originalecommerce.utils.Constants
import com.example.originalecommerce.viewmodels.MainViewModel
import com.example.orignal_ecommerce_manger.util.StatusResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductCatigoryFragment : Fragment() {
    private var _binding: FragmentProductCatigoryBinding?=null
    private val binding get() = _binding!!

    private val navArgs by navArgs<ProductCatigoryFragmentArgs>()

    private val mainViewModel by viewModels<MainViewModel>()
    private val prodAdapter by lazy {
        ProductItemAdapter(mainViewModel,viewLifecycleOwner,Constants.PRODCAT_CONTAINER)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding= FragmentProductCatigoryBinding.inflate(layoutInflater)
        setUpView()
        return binding.root
    }

    private fun setUpView() {
        setRecy()
        binding.btnBackProbycat.setOnClickListener {
            findNavController().popBackStack()
        }
        val catName=navArgs.catname
        if (!catName.isNullOrEmpty())
        {
            mainViewModel.getProductWithCatigory(catName)

        }
        else{
            mainViewModel.getProductWithCatigory("")
        }
        mainViewModel.prodWithCatigory.observe(viewLifecycleOwner,{
            when {
                it is StatusResult.Success -> {
                    showEmptyData(false)
                    binding.progressBar2.visibility=View.INVISIBLE
                    prodAdapter.setData(it.data!!)
                }
                it is StatusResult.Error -> {
                    showEmptyData(true)
                    binding.progressBar2.visibility=View.INVISIBLE
                }
                it is StatusResult.Loading -> {
                    showEmptyData(false)
                    binding.progressBar2.visibility=View.VISIBLE
                }
            }
        })
    }

    fun setRecy()
    {
        binding.recyProdcat.apply {
            adapter=prodAdapter
            layoutManager=GridLayoutManager(requireContext(),2)
        }
    }

    fun showEmptyData(show:Boolean)
    {
        if (show)
        {
            binding.imgErrorProdcat.visibility=View.VISIBLE
            binding.tvErrorProduct.visibility=View.VISIBLE
        }else{
            binding.imgErrorProdcat.visibility=View.INVISIBLE
            binding.tvErrorProduct.visibility=View.INVISIBLE
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}