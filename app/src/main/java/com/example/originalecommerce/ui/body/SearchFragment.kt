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
import com.example.originalecommerce.R
import com.example.originalecommerce.adapters.ProductItemAdapter
import com.example.originalecommerce.databinding.FragmentProductCatigoryBinding
import com.example.originalecommerce.databinding.FragmentSearchBinding
import com.example.originalecommerce.utils.Constants
import com.example.originalecommerce.viewmodels.MainViewModel
import com.example.orignal_ecommerce_manger.util.StatusResult
import com.example.orignal_ecommerce_manger.util.observerOnce
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding?=null
    private val binding get() = _binding!!

    private val mainViewModel by viewModels<MainViewModel>()
    private val searchAdapter by lazy {
        ProductItemAdapter(mainViewModel,viewLifecycleOwner, Constants.SEARCH_CONTAINER)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentSearchBinding.inflate(layoutInflater)
        setUPView()
        return binding.root
    }

    private fun setUPView() {
        setUpRecy()
        binding.btnBackSearch.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.fabSearch.setOnClickListener {
            val name=binding.etSearch.text.toString()
            if (name.isEmpty())
            {

            }else{
                mainViewModel.getProductSearch(name)
                mainViewModel.search.observe(viewLifecycleOwner,{
                    when {
                        it is StatusResult.Success -> {
                            searchAdapter.setData(it.data!!)
                            binding.pbSearch.visibility=View.INVISIBLE
                            showEmptyData(false)
                        }
                        it is StatusResult.Error -> {
                            showEmptyData(true)
                            binding.pbSearch.visibility=View.INVISIBLE
                        }
                        it is StatusResult.Loading -> {
                            showEmptyData(false)
                            binding.pbSearch.visibility=View.VISIBLE
                        }
                    }
                })
            }

        }
    }

    private fun setUpRecy() {
        binding.recySearch.apply {
            adapter=searchAdapter
            layoutManager=GridLayoutManager(requireActivity(),2)
        }
    }

    fun showEmptyData(show:Boolean)
    {
        if (show)
        {
            binding.imgErrorSearch.visibility=View.VISIBLE
            binding.tvErrorSearch.visibility=View.VISIBLE
        }else{
            binding.imgErrorSearch.visibility=View.INVISIBLE
            binding.tvErrorSearch.visibility=View.INVISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}