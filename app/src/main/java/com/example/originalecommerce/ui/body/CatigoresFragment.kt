package com.example.originalecommerce.ui.body

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.originalecommerce.R
import com.example.originalecommerce.adapters.ProductItemAdapter
import com.example.originalecommerce.databinding.FragmentCatigoresBinding
import com.example.originalecommerce.databinding.FragmentChoseAtributeBinding
import com.example.originalecommerce.utils.Constants
import com.example.originalecommerce.viewmodels.MainViewModel
import com.example.orignal_ecommerce_manger.util.StatusResult
import com.example.orignal_ecommerce_manger.util.observerOnce
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatigoresFragment : Fragment() {
    private var _binding: FragmentCatigoresBinding?=null
    private val binding get() = _binding!!

    private val mMainViewModel by viewModels<MainViewModel>()

    private val mAdapter by lazy {
        ProductItemAdapter(mMainViewModel,viewLifecycleOwner,Constants.CATIGORES_CONTAINER)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentCatigoresBinding.inflate(layoutInflater)
        setupView()
        return binding.root
    }

    private fun setupView() {
        setUpRecy()
        setAllCheaked()
        mMainViewModel.getProductType("")
        mMainViewModel.productType.observe(viewLifecycleOwner,{
            when {
                it is StatusResult.Success -> {
                    showProgress(false)
                    showErrorView(false)
                    mAdapter.setData(it.data!!)
                }
                it is StatusResult.Error -> {
                    showProgress(false)
                    mAdapter.setData(mutableListOf())
                    showErrorView(true,it.masg!!)
                }
                it is StatusResult.Loading -> {
                    showProgress(true)
                    showErrorView(false)
                }
            }
        })
        binding.btnAll.setOnClickListener {
            mMainViewModel.getProductType("")
            setAllCheaked()
        }
        binding.btnMan.setOnClickListener {
            mMainViewModel.getProductType(Constants.TYPE_MAN)
            setManCheaked()
        }
        binding.btnWoman.setOnClickListener {
            mMainViewModel.getProductType(Constants.TYPE_WOMAN)
            setWomanCheaked()
        }
        binding.btnChild.setOnClickListener {
            mMainViewModel.getProductType(Constants.TYPE_CHILD)
            setChiledCheaked()
        }
        binding.btnBackCatigores.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun setUpRecy() {
        binding.recyCatigores.apply {
            adapter=mAdapter
            layoutManager=GridLayoutManager(requireActivity(),2)
        }
    }

    private fun showProgress(show:Boolean)
    {
        if (show)
        {
            binding.progressBar4.visibility=View.VISIBLE
        }else{
            binding.progressBar4.visibility=View.INVISIBLE
        }
    }

    private fun showErrorView(show:Boolean,msg:String="")
    {
        if (show)
        {
            binding.imgErrorCatigores.visibility=View.VISIBLE
            binding.tvErrorCatigores.visibility=View.VISIBLE
        }else{
            binding.imgErrorCatigores.visibility=View.INVISIBLE
            binding.tvErrorCatigores.visibility=View.INVISIBLE
        }
    }

    private fun setAllCheaked()
    {
        binding.btnAll.setTextColor(ContextCompat.getColor(requireContext(),R.color.myred))
        binding.btnAll.setIconTintResource(R.color.myred)
        binding.btnMan.setTextColor(ContextCompat.getColor(requireContext(),R.color.darkgray))
        binding.btnMan.setIconTintResource(R.color.darkgray)
        binding.btnWoman.setTextColor(ContextCompat.getColor(requireContext(),R.color.darkgray))
        binding.btnWoman.setIconTintResource(R.color.darkgray)
        binding.btnChild.setTextColor(ContextCompat.getColor(requireContext(),R.color.darkgray))
        binding.btnChild.setIconTintResource(R.color.darkgray)
    }

    private fun setManCheaked()
    {
        binding.btnAll.setTextColor(ContextCompat.getColor(requireContext(),R.color.darkgray))
        binding.btnAll.setIconTintResource(R.color.darkgray)
        binding.btnMan.setTextColor(ContextCompat.getColor(requireContext(),R.color.myred))
        binding.btnMan.setIconTintResource(R.color.myred)
        binding.btnWoman.setTextColor(ContextCompat.getColor(requireContext(),R.color.darkgray))
        binding.btnWoman.setIconTintResource(R.color.darkgray)
        binding.btnChild.setTextColor(ContextCompat.getColor(requireContext(),R.color.darkgray))
        binding.btnChild.setIconTintResource(R.color.darkgray)
    }

    private fun setWomanCheaked()
    {
        binding.btnAll.setTextColor(ContextCompat.getColor(requireContext(),R.color.darkgray))
        binding.btnAll.setIconTintResource(R.color.darkgray)
        binding.btnMan.setTextColor(ContextCompat.getColor(requireContext(),R.color.darkgray))
        binding.btnMan.setIconTintResource(R.color.darkgray)
        binding.btnWoman.setTextColor(ContextCompat.getColor(requireContext(),R.color.myred))
        binding.btnWoman.setIconTintResource(R.color.myred)
        binding.btnChild.setTextColor(ContextCompat.getColor(requireContext(),R.color.darkgray))
        binding.btnChild.setIconTintResource(R.color.darkgray)
    }

    private fun setChiledCheaked()
    {
        binding.btnAll.setTextColor(ContextCompat.getColor(requireContext(),R.color.darkgray))
        binding.btnAll.setIconTintResource(R.color.darkgray)
        binding.btnMan.setTextColor(ContextCompat.getColor(requireContext(),R.color.darkgray))
        binding.btnMan.setIconTintResource(R.color.darkgray)
        binding.btnWoman.setTextColor(ContextCompat.getColor(requireContext(),R.color.darkgray))
        binding.btnWoman.setIconTintResource(R.color.darkgray)
        binding.btnChild.setTextColor(ContextCompat.getColor(requireContext(),R.color.myred))
        binding.btnChild.setIconTintResource(R.color.myred)
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}