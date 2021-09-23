package com.example.originalecommerce.ui.body

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.originalecommerce.R
import com.example.originalecommerce.databinding.ActivityBodyBinding
import dagger.hilt.android.AndroidEntryPoint
import meow.bottomnavigation.MeowBottomNavigation

@AndroidEntryPoint
class BodyActivity : AppCompatActivity() {
    private var _binding:ActivityBodyBinding?=null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding= ActivityBodyBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}