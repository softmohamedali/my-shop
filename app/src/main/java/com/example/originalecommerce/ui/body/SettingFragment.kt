package com.example.originalecommerce.ui.body

import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.findNavController
import com.example.originalecommerce.R
import com.example.originalecommerce.databinding.FragmentSearchBinding
import com.example.originalecommerce.databinding.FragmentSettingBinding
import java.util.*

class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding?=null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentSettingBinding.inflate(layoutInflater)
        binding.btnBackSetting.setOnClickListener {
            findNavController().popBackStack()
        }
        if (AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES)
        {
            binding.switchDark.isChecked=true
        }
        binding.switchDark.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked)
            {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
        binding.switchArbic.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked)
            {
                binding.switchArbic.setText("اللغه العربيه")
                binding.switchDark.setText("الوضع الليلي")
                binding.textsetting.setText("الاعدادات")
                binding.switchArbic.gravity=Gravity.END
                binding.switchDark.gravity=Gravity.END
            }else{
                binding.switchArbic.setText("Arabic Languashe")
                binding.switchDark.setText("Dark Mode")
                binding.textsetting.setText("setting")
                binding.switchArbic.gravity=Gravity.START
                binding.switchDark.gravity=Gravity.START
            }
        }
        return binding.root
    }


    private fun changeLang(ch:Boolean)
    {
        var str="en"
        if (ch)
        {
            str="ar"
        }
        val res=requireContext().resources
        val con=res.configuration
        con.setLocale(Locale(str))
        res.updateConfiguration(con,res.displayMetrics)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}