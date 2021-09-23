package com.example.originalecommerce.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.originalecommerce.R
import com.example.originalecommerce.databinding.FragmentLogInBinding
import com.example.originalecommerce.ui.body.BodyActivity
import com.example.originalecommerce.viewmodels.AuthViewModel
import com.example.orignal_ecommerce_manger.util.StatusResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogInFragment : Fragment() {
    private var _binding: FragmentLogInBinding?=null
    private val binding get() = _binding!!

    private val authViewModek by viewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentLogInBinding.inflate(layoutInflater)

        setUpView()
        return binding.root
    }

    private fun setUpView()
    {
        binding.btnLoginfragLogin.setOnClickListener {
            logIn()
        }
        binding.tvGotoregister.setOnClickListener {
            findNavController().navigate(R.id.action_logInFragment_to_registerFragment)
        }
        binding.tvForget.setOnClickListener {
            findNavController().navigate(R.id.action_logInFragment_to_forgetPassFragment)
        }
        authViewModek.isLogin.observe(viewLifecycleOwner,{
            when{
                it is StatusResult.Error ->{
                    binding.proLoginfrag.visibility=View.INVISIBLE
                    Toast.makeText(requireActivity(),"${it.masg}", Toast.LENGTH_SHORT).show()
                }
                it is StatusResult.Success ->{
                    binding.proLoginfrag.visibility=View.INVISIBLE
                    startActivity(Intent(requireActivity(),BodyActivity::class.java))
                    requireActivity().finish()
                }
                it is StatusResult.Loading ->{
                    binding.proLoginfrag.visibility=View.VISIBLE
                }
            }
        })
    }

    private fun  logIn()
    {
        val email=binding.etLoginfragEmail.text.toString()
        val pass=binding.etLoginfragPass.text.toString()


        if (email.trim().isEmpty())
        {
            binding.etLoginfragEmail.error="Email require"
            binding.etLoginfragEmail.requestFocus()
            return
        }
        if (pass.trim().isEmpty())
        {
            binding.etLoginfragPass.error="Password require"
            binding.etLoginfragPass.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            binding.etLoginfragEmail.error="Invalid Email"
            binding.etLoginfragEmail.requestFocus()
            return
        }


        authViewModek.logIn(email, pass)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}