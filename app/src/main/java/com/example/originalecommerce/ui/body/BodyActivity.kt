package com.example.originalecommerce.ui.body

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.originalecommerce.R
import com.example.originalecommerce.databinding.ActivityBodyBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BodyActivity : AppCompatActivity(),NavController.OnDestinationChangedListener{
    private var _binding:ActivityBodyBinding?=null
    private val binding get() = _binding!!
    private lateinit var navController:NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding= ActivityBodyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController=findNavController(R.id.nav_controller_mian)
        binding.bottomNavigationView.setupWithNavController(navController)
        val navHostFragment=supportFragmentManager.findFragmentById(R.id.nav_controller_mian) as NavHostFragment
        val navController=navHostFragment.navController
        navController.addOnDestinationChangedListener(this)

    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when(destination.id)
        {
            R.id.userFragment,R.id.mainBodyFragment,R.id.favFragment,
            R.id.myCartFragment,R.id.catigoresFragment->{
                binding.bottomNavigationView.visibility=View.VISIBLE
            }
            R.id.showPeoductFragment ->{
                binding.bottomNavigationView.visibility=View.GONE
            }
            else ->{
                binding.bottomNavigationView.visibility=View.GONE
            }
        }
    }

    override fun onNavigateUp(): Boolean {
        return super.onNavigateUp()||navController.navigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }


}


