package com.example.originalecommerce.ui.body

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.originalecommerce.R
import com.example.originalecommerce.adapters.ProductItemAdapter
import com.example.originalecommerce.databinding.FragmentProductCatigoryBinding
import com.example.originalecommerce.databinding.FragmentShowOrderBinding
import com.example.originalecommerce.utils.Constants
import com.example.originalecommerce.viewmodels.MainViewModel


class ShowOrderFragment : Fragment() {
    private var _binding: FragmentShowOrderBinding?=null
    private val binding get() = _binding!!

    private val navArgs by navArgs<ProductCatigoryFragmentArgs>()

    private val mainViewModel by viewModels<MainViewModel>()
    private val prodAdapter by lazy {
        ProductItemAdapter(mainViewModel,viewLifecycleOwner, Constants.PRODCAT_CONTAINER)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentShowOrderBinding.inflate(layoutInflater)

        return binding.root
    }

    fun setUpView(){
        setRecy()
    }

    fun setRecy()
    {
        binding.recyOrder.apply {
            adapter=prodAdapter
            layoutManager= GridLayoutManager(requireContext(),2)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}