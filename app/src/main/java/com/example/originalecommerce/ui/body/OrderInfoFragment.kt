package com.example.originalecommerce.ui.body

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.originalecommerce.R
import com.example.originalecommerce.databinding.FragmentOrderInfoBinding
import com.example.originalecommerce.utils.Constants
import com.example.orignal_ecommerce_manger.util.mySnack
import com.example.orignal_ecommerce_manger.util.myToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class OrderInfoFragment : Fragment() {
    private var _binding: FragmentOrderInfoBinding?=null
    private val binding get() = _binding!!
    private var languid:String?=null
    private var latuid:String?=null
    private val navArgs by navArgs<OrderInfoFragmentArgs>()

    @Inject
    lateinit var dataStore: DataStore<Preferences>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentOrderInfoBinding.inflate(layoutInflater)
        setUpView()
        return binding.root
    }

    private fun setUpView()
    {
        fetchInfoData()
        setDataPlaceEt()
        languid=navArgs.latlong
        latuid=navArgs.lattuid
        binding.cbLocationCheakedClientinfofrag.isChecked =
            !languid.isNullOrEmpty()||!latuid.isNullOrEmpty()

        binding.btnBackOrderinfo.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnCheaklocationClientinfofrag.setOnClickListener {
            findNavController().navigate(R.id.action_orderInfoFragment_to_mapsFragment)
        }
        binding.btnConfpaymentClientpayinfo.setOnClickListener {
            lifecycleScope.launch {
                saveData()
            }
        }
    }

    suspend fun saveData()
    {
        val fName= binding.etFirstnamaeClientpayinfo.text.toString()
        val lName= binding.etLastnameClientpayinfo.text.toString()
        val numOrnameBuild= binding.etNumornamebuildClientpayinfo.text.toString()
        val fNum= binding.etFloorClientpayinfo.text.toString()
        val famMarker= binding.etMarkerClientpayinfo.text.toString()
        val placeType= binding.etPlacetypeClientpayinfo.text.toString()
        val phone= binding.etPhonenumberClientpayinfo.text.toString()
        val note=binding.etNoteClientpayinfo.text.toString()


        if (fName.isEmpty()){
            binding.etFirstnamaeClientpayinfo.error="this field require"
            binding.etFirstnamaeClientpayinfo.requestFocus()
            return
        }
        if (lName.isEmpty()){
            binding.etLastnameClientpayinfo.error="this field require"
            binding.etLastnameClientpayinfo.requestFocus()
            return
        }
        if (numOrnameBuild.isEmpty()){
            binding.etNumornamebuildClientpayinfo.error="this field require"
            binding.etNumornamebuildClientpayinfo.requestFocus()
            return
        }
        if (fNum.isEmpty()){
            binding.etFloorClientpayinfo.error="this field require"
            binding.etFloorClientpayinfo.requestFocus()
            return
        }
        if (famMarker.isEmpty()){
            binding.etMarkerClientpayinfo.error="this field require"
            binding.etMarkerClientpayinfo.requestFocus()
            return
        }
        if (placeType.isEmpty()){
            binding.etPlacetypeClientpayinfo.error="this field require"
            binding.etPlacetypeClientpayinfo.requestFocus()
            return
        }
        if (phone.isEmpty()){
            binding.etPhonenumberClientpayinfo.error="this field require"
            binding.etPhonenumberClientpayinfo.requestFocus()
            return
        }
        if (note.isEmpty()){
            binding.etNoteClientpayinfo.error="this field require"
            binding.etNoteClientpayinfo.requestFocus()
            return
        }
        if (languid.isNullOrEmpty()||latuid.isNullOrEmpty()){
            myToast(requireContext(),"${languid}")
            Toast.makeText(requireActivity(),"Location is Not Specify",Toast.LENGTH_SHORT).show()
            return
        }

        saveDataPrefrences(
            fName,
            lName,
            numOrnameBuild,
            fNum,
            famMarker,
            placeType,
            phone,
            note,
            languid!!,
            latuid!!
        )
        if(!cheakPayMethod())
        {
            findNavController().navigate(R.id.action_orderInfoFragment_to_paymentMethodFragment)
        }else{
            findNavController().navigate(R.id.action_orderInfoFragment_to_myCartFragment)
        }
        mySnack(requireActivity(),binding.root,"Info Saved")
    }

    private suspend fun saveDataPrefrences(
        fName: String,
        lName: String,
        numOrnameBuild: String,
        fNum: String,
        famMarker: String,
        placeType: String,
        phone: String,
        note: String,
        languid: String,
        latuid: String
    ) {
        val fNameSP= stringPreferencesKey(Constants.FIRST_NAME_SP)
        val lNameSP= stringPreferencesKey(Constants.LAST_NAME_SP)
        val numOrnameBuildSP= stringPreferencesKey(Constants.NAMEORNUM_BUILD_SP)
        val fNumSP= stringPreferencesKey(Constants.FLOOR_NUM_SP)
        val famMarkerSP= stringPreferencesKey(Constants.MARKER_SP)
        val placeTypeSP= stringPreferencesKey(Constants.PLACE_TYPE_SP)
        val phoneSP= stringPreferencesKey(Constants.PHONE_SP)
        val noteSP= stringPreferencesKey(Constants.NOTE_SP)
        val langSP= stringPreferencesKey(Constants.LANG_SP)
        val latSP= stringPreferencesKey(Constants.LAT_SP)

        dataStore.edit {
            it[fNameSP]=fName
            it[lNameSP]=lName
            it[numOrnameBuildSP]=numOrnameBuild
            it[fNumSP]=fNum
            it[famMarkerSP]=famMarker
            it[placeTypeSP]=placeType
            it[phoneSP]=phone
            it[noteSP]=note
            it[langSP]=languid
            it[latSP]=latuid
        }
    }

    private fun fetchInfoData()
    {
        lifecycleScope.launch{
            val prefrences=dataStore.data.first()

            val fNameSP= stringPreferencesKey(Constants.FIRST_NAME_SP)
            val lNameSP= stringPreferencesKey(Constants.LAST_NAME_SP)
            val numOrnameBuildSP= stringPreferencesKey(Constants.NAMEORNUM_BUILD_SP)
            val fNumSP= stringPreferencesKey(Constants.FLOOR_NUM_SP)
            val famMarkerSP= stringPreferencesKey(Constants.MARKER_SP)
            val placeTypeSP= stringPreferencesKey(Constants.PLACE_TYPE_SP)
            val phoneSP= stringPreferencesKey(Constants.PHONE_SP)
            val noteSP= stringPreferencesKey(Constants.NOTE_SP)
            val langSP= stringPreferencesKey(Constants.LANG_SP)
            val latSP= stringPreferencesKey(Constants.LAT_SP)

            binding.etFirstnamaeClientpayinfo.setText(prefrences[fNameSP])
            binding.etLastnameClientpayinfo.setText(prefrences[lNameSP])
            binding.etNumornamebuildClientpayinfo.setText(prefrences[numOrnameBuildSP])
            binding.etFloorClientpayinfo.setText(prefrences[fNumSP])
            binding.etMarkerClientpayinfo.setText(prefrences[famMarkerSP])
            binding.etPlacetypeClientpayinfo.setText(prefrences[placeTypeSP])
            binding.etPhonenumberClientpayinfo.setText(prefrences[phoneSP])
            binding.etNoteClientpayinfo.setText(prefrences[noteSP])
            languid=prefrences[langSP]
            latuid=prefrences[latSP]
            binding.cbLocationCheakedClientinfofrag.isChecked =
                !languid.isNullOrEmpty()||!latuid.isNullOrEmpty()

        }

    }

    private fun cheakPayMethod():Boolean
    {
        var prefrences:Preferences?=null
        lifecycleScope.launch {
            prefrences=dataStore.data.first()
        }
        val cvv= prefrences?.get(stringPreferencesKey(Constants.CVV2_SP))
        return cvv!=null
    }

    private fun setDataPlaceEt()
    {
        val places= arrayOf("home","work","else")
        val adapter=ArrayAdapter(requireActivity(),R.layout.layout_drop_et,places)
        binding.etPlacetypeClientpayinfo.setAdapter(adapter)
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