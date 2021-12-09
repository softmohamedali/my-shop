package com.example.originalecommerce.ui.body

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.originalecommerce.R
import com.example.originalecommerce.adapters.TrackOrderAdapter
import com.example.originalecommerce.data.local.entitys.OrderEntity
import com.example.originalecommerce.databinding.FragmentSearchBinding
import com.example.originalecommerce.databinding.FragmentShowMyOrderBinding
import com.example.originalecommerce.models.AllREquests
import com.example.originalecommerce.viewmodels.MainViewModel
import com.example.orignal_ecommerce_manger.util.StatusResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowMyOrderFragment : Fragment(),TrackOrderAdapter.TrackItemListener {
    private var _binding: FragmentShowMyOrderBinding?=null
    private val binding get() = _binding!!

    private val mainViewModel by viewModels<MainViewModel>()
    private val mAdapter by lazy { TrackOrderAdapter(this) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentShowMyOrderBinding.inflate(layoutInflater)
        setUpView()
        return binding.root
    }

    private fun setUpView() {
        setUpRecy()
        binding.btnBackMyoreders.setOnClickListener {
            findNavController().popBackStack()
        }
        mainViewModel.getAllPayments()
        mainViewModel.myPaymnts.observe(viewLifecycleOwner,{
            when{
                it is StatusResult.Error ->{
                    showProgress(false)
                    showErrorView(true,it.masg!!)
                    mAdapter.setData(mutableListOf())
                }
                it is StatusResult.Success ->{
                    showErrorView(false)
                    showProgress(false)
                    mAdapter.setData(it.data!!)
                }
                it is StatusResult.Loading ->{
                    showProgress(true)
                    showErrorView(false)
                }
            }
        })
    }

    private fun setUpRecy() {
        binding.recyMyorders.apply {
            adapter=mAdapter
            layoutManager=LinearLayoutManager(requireActivity(),RecyclerView.VERTICAL,false)
        }

    }

    private fun showProgress(show:Boolean)
    {
        if (show)
        {
            binding.progressBar3.visibility=View.VISIBLE
        }else{
            binding.progressBar3.visibility=View.INVISIBLE
        }
    }

    private fun showErrorView(show:Boolean,msg:String="")
    {
        if (show)
        {
            binding.imageView2.visibility=View.VISIBLE
            binding.textView5.visibility=View.VISIBLE
        }else{
            binding.imageView2.visibility=View.INVISIBLE
            binding.textView5.visibility=View.INVISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    override fun onItemClik(items: MutableList<OrderEntity>) {
        var allrequests=AllREquests(items)
        val action=ShowMyOrderFragmentDirections.actionShowMyOrderFragmentToShowMyRequestFragment(allrequests)
        findNavController().navigate(action)
    }

}