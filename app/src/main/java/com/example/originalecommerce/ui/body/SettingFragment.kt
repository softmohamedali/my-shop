package com.example.originalecommerce.ui.body

import android.content.Context
import android.content.SharedPreferences
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
    lateinit var shared:SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentSettingBinding.inflate(layoutInflater)
        shared=requireContext().getSharedPreferences("lang",Context.MODE_PRIVATE)
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
                changeLang("ar")
                saveShared("ar")
                binding.switchArbic.gravity=Gravity.END
                binding.switchDark.gravity=Gravity.END
            }else{
                changeLang("en")
                saveShared("en")
                binding.switchArbic.gravity=Gravity.START
                binding.switchDark.gravity=Gravity.START
            }
        }
        val langushe=getLangSha()
        binding.switchArbic.isChecked = langushe != "en"
        return binding.root
    }


    private fun changeLang(localcode:String)
    {
        val local=Locale(localcode)
        Locale.setDefault(local)
        val resourses=requireActivity().resources
        val config=resourses.configuration
        config.setLocale(local)
        resourses.updateConfiguration(config,resourses.displayMetrics)
    }

    fun saveShared(lang:String)
    {
        val editor=shared.edit()
        editor.apply{
            putString("lang",lang)
            apply()
        }
    }

    fun getLangSha():String{
        val lang=shared.getString("lang","en")
        return lang!!
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}