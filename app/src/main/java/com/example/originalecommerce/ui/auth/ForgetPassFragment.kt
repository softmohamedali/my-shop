package com.example.originalecommerce.ui.auth

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
import com.example.originalecommerce.databinding.FragmentForgetPassBinding
import com.example.originalecommerce.databinding.FragmentLogInBinding
import com.example.originalecommerce.viewmodels.AuthViewModel
import com.example.orignal_ecommerce_manger.util.StatusResult
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class ForgetPassFragment : Fragment() {
    private var _binding: FragmentForgetPassBinding?=null
    private val binding get() = _binding!!

    private val authViewModek by viewModels<AuthViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding= FragmentForgetPassBinding.inflate(layoutInflater)

        binding.btnBackForget.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnSendlink.setOnClickListener {
            val mail=binding.etForgetfragEmail.text.toString()
            if (mail.isEmpty())
            {
                binding.etForgetfragEmail.error="please enter email"
                binding.etForgetfragEmail.requestFocus()
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches())
            {
                binding.etForgetfragEmail.error="please enter valid email"
                binding.etForgetfragEmail.requestFocus()
                return@setOnClickListener
            }
            authViewModek.resetPasswoed(mail)
        }

        authViewModek.resetPassword.observe(viewLifecycleOwner,{
            when{
                it is StatusResult.Success ->{
                    Snackbar.make(binding.root,"Link send Successfuly",Snackbar.LENGTH_SHORT)
                        .setAction("ok",{})
                        .show()
                    binding.progressBar.visibility=View.GONE
                }
                it is StatusResult.Error ->{
                    Toast.makeText(requireActivity(),it.masg,Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility=View.GONE
                }
                it is StatusResult.Loading ->{
                    binding.progressBar.visibility=View.VISIBLE
                }
            }
        })

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}
