package com.example.originalecommerce.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.example.originalecommerce.utils.Constants
import com.example.originalecommerce.viewmodels.AuthViewModel
import com.example.orignal_ecommerce_manger.util.StatusResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogInFragment : Fragment() {
    private var _binding: FragmentLogInBinding?=null
    private val binding get() = _binding!!
    private lateinit var googleSignInOptions:GoogleSignInOptions
    private lateinit var mAuth:FirebaseAuth
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
        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(requireActivity(),googleSignInOptions )
        mAuth= FirebaseAuth.getInstance()
        binding.btnLoginfragLogin.setOnClickListener {
            logIn()
        }
        binding.tvGotoregister.setOnClickListener {
            findNavController().navigate(R.id.action_logInFragment_to_registerFragment)
        }
        binding.tvForget.setOnClickListener {
            findNavController().navigate(R.id.action_logInFragment_to_forgetPassFragment)
        }
        binding.btnSinginfragSinggoogle.setOnClickListener {
            val intent=googleSignInClient.signInIntent
            startActivityForResult(intent, Constants.RC_SING_IN)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == Constants.RC_SING_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception=task.exception
            if (task.isSuccessful)
            {
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d("lole", "suc1")
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.d("lole", "fil1")
                }
            }else
            {
                Log.d("lole", exception?.toString()+"1fe")
            }

        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("lole", "suc2")
                    startActivity(Intent(requireActivity(),BodyActivity::class.java))
                    requireActivity().finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.d("Lole", "file2")
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}