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
import com.example.originalecommerce.adapters.NotifiAdapter
import com.example.originalecommerce.databinding.FragmentNotificationBinding
import com.example.originalecommerce.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment : Fragment() {
    private var _binding:FragmentNotificationBinding?=null
    private val binding get() = _binding!!

    private val mViewModel by viewModels<MainViewModel>()

    private val mAdapter by lazy { NotifiAdapter()}
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentNotificationBinding.inflate(layoutInflater)
        setUpRecy()
        binding.btnBackNotifi.setOnClickListener {
            findNavController().popBackStack()
        }

        mViewModel.readNotification.observe(viewLifecycleOwner,{
            if (it.isEmpty())
            {
                showEroow(true)
            } else{
                mAdapter.setData(it)
                showEroow(false)
            }
        })

        return binding.root
    }

    private fun setUpRecy()
    {
        binding.recyNotif.apply {
            adapter=mAdapter
            layoutManager=LinearLayoutManager(requireActivity(),RecyclerView.VERTICAL,false)
        }
    }

    private fun showEroow(show:Boolean){
        if (show){
            binding.imgErrorNoti.visibility=View.VISIBLE
            binding.tvErrorNoti.visibility=View.VISIBLE
        }else{
            binding.imgErrorNoti.visibility=View.INVISIBLE
            binding.tvErrorNoti.visibility=View.INVISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}