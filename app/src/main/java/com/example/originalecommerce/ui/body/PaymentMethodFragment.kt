package com.example.originalecommerce.ui.body

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.originalecommerce.R
import com.example.originalecommerce.databinding.FragmentPaymentMethodBinding
import com.example.originalecommerce.utils.Constants
import com.example.orignal_ecommerce_manger.util.mySnack
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PaymentMethodFragment : Fragment() {
    private var _binding: FragmentPaymentMethodBinding?=null
    private val binding get() = _binding!!

    @Inject
    lateinit var dataStore: DataStore<Preferences>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentPaymentMethodBinding.inflate(layoutInflater)
        binding.btnBackPaymeth.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnSetmethod.setOnClickListener {
            savePaymentMethoud()

        }
        fetchInfoData()
        return binding.root
    }

    private fun savePaymentMethoud() {
        val cartName= binding.etNamecart.text.toString()
        val cartId= binding.etCartid.text.toString()
        val cartCvv2= binding.etCvv2.text.toString()
        val cartValid=binding.etValid.text.toString()


        if (cartName.isEmpty()){
            binding.etNamecart.error="this field require"
            binding.etNamecart.requestFocus()
            return
        }
        if (cartId.isEmpty()){
            binding.etCartid.error="this field require"
            binding.etCartid.requestFocus()
            return
        }
        if (cartCvv2.isEmpty()){
            binding.etCvv2.error="this field require"
            binding.etCvv2.requestFocus()
            return
        }
        if (cartValid.isEmpty()){
            binding.etValid.error="this field require"
            binding.etValid.requestFocus()
            return
        }
        saveDataPrefrences(
            cartName,
            cartId,
            cartCvv2,
            cartValid
        )
        findNavController().popBackStack()
        mySnack(requireActivity(),binding.root,"Payment info saved")
    }

    private fun saveDataPrefrences(
        cartName: String,
        cartId: String,
        cartCvv2: String,
        cartValid: String
    ) {
        val nameSP= stringPreferencesKey(Constants.CART_NAME_SP)
        val idSP= stringPreferencesKey(Constants.CART_ID_SP)
        val cvv2SP= stringPreferencesKey(Constants.CVV2_SP)
        val validSP= stringPreferencesKey(Constants.VALID_YH_SP)

        lifecycleScope.launch {
            dataStore.edit {
                it[nameSP]=cartName
                it[idSP]=cartId
                it[cvv2SP]=cartCvv2
                it[validSP]=cartValid
            }
        }
    }


    private fun fetchInfoData()
    {
        lifecycleScope.launch {
            val prefrences=dataStore.data.first()

            val nameSP= stringPreferencesKey(Constants.CART_NAME_SP)
            val idSP= stringPreferencesKey(Constants.CART_ID_SP)
            val cvv2SP= stringPreferencesKey(Constants.CVV2_SP)
            val validSP= stringPreferencesKey(Constants.VALID_YH_SP)

            binding.etNamecart.setText(prefrences[nameSP])
            binding.etCartid.setText(prefrences[idSP])
            binding.etCvv2.setText(prefrences[cvv2SP])
            binding.etValid.setText(prefrences[validSP])

        }

    }

    private fun showToast()
    {
        val toast=Toast(requireActivity())
        toast.duration=Toast.LENGTH_SHORT
        toast.setGravity(Gravity.CENTER,0,0)
        toast.view=LayoutInflater.from(requireActivity()).inflate(R.layout.layout_succes,null,false)
        toast.show()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}