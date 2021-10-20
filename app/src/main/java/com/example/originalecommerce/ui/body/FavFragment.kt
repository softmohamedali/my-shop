package com.example.originalecommerce.ui.body

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.originalecommerce.R
import com.example.originalecommerce.adapters.FavItemAdapter
import com.example.originalecommerce.adapters.ProductItemAdapter
import com.example.originalecommerce.databinding.FragmentFavBinding
import com.example.originalecommerce.databinding.FragmentUserBinding
import com.example.originalecommerce.viewmodels.AuthViewModel
import com.example.originalecommerce.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavFragment : Fragment() {
    private var _binding: FragmentFavBinding?=null
    private val binding get() = _binding!!

    private val mMainVM by viewModels<MainViewModel>()

    private val mFavAdapter by lazy { FavItemAdapter(mMainVM,requireActivity()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentFavBinding.inflate(layoutInflater)
        setUpView()
        return binding.root
    }

    private fun setUpView()
    {
        setRecy()
        binding.btnBackFav.setOnClickListener{
            findNavController().popBackStack()
        }
        mMainVM.readFavEntity.observe(viewLifecycleOwner,{
            if (it.isEmpty())
            {
                showEmptyFavs(true)
                mFavAdapter.setData(mutableListOf())
            }else{
                showEmptyFavs(false)
                mFavAdapter.setData(it)
            }
        })

    }

    private fun setRecy()
    {
        binding.recyFav.apply {
            adapter=mFavAdapter
            layoutManager=GridLayoutManager(requireActivity(),2)
        }
    }

    fun showEmptyFavs(show:Boolean)
    {
        if (show)
        {
            binding.imgErrorFavfrag.visibility=View.VISIBLE
            binding.tvErrorFavfrag.visibility=View.VISIBLE

        }else{
            binding.imgErrorFavfrag.visibility=View.INVISIBLE
            binding.tvErrorFavfrag.visibility=View.INVISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}