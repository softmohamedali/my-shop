package com.example.originalecommerce.ui.body

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.denzcoskun.imageslider.constants.ScaleTypes
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.originalecommerce.R
import com.example.originalecommerce.adapters.CatigoryItemAdapter
import com.example.originalecommerce.adapters.ProductItemAdapter
import com.example.originalecommerce.databinding.FragmentMainBodyBinding
import com.example.originalecommerce.viewmodels.MainViewModel
import com.example.orignal_ecommerce_manger.util.StatusResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainBodyFragment : Fragment() {
    private var _binding:FragmentMainBodyBinding?=null
    private  val binidng get() = _binding!!

    private val mViewModel by viewModels<MainViewModel>()

    private val mProductAdapter by lazy { ProductItemAdapter() }
    private val mProductBestSaleAdapter by lazy { ProductItemAdapter() }
    private val mCatigoryAdapter by lazy { CatigoryItemAdapter() }


    private lateinit var imgSliderList:ArrayList<SlideModel>
    private lateinit var imageSlider:ImageSlider
    var dotssize: Int?=null
    private val dots= mutableListOf<TextView>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentMainBodyBinding.inflate(layoutInflater)
        setUpView()
        return binidng.root
    }

    fun setUpView()
    {
        setUpRecyclerView()
        binidng.imgUserMainfrag
        imgSliderList = ArrayList<SlideModel>() // Create image list
        imageSlider = binidng.imgMainfragSlideroffers
        imageSlider.setItemClickListener(object : ItemClickListener {
            override fun onItemSelected(position: Int) {
                findNavController().navigate(R.id.action_mainBodyFragment_to_productsQueryFragment)
            }
        })


        mViewModel.getAllCatigory()
        mViewModel.catigory.observe(viewLifecycleOwner,{
            when{
                it is StatusResult.Success->{
                    mCatigoryAdapter.setData(it.data!!)
                    showShimmercatigory(false)
                }
                it is StatusResult.Error ->{
                    showShimmercatigory(true)                }
                it is StatusResult.Loading ->{
                    showShimmercatigory(true)                }
            }
        })


        mViewModel.getAllProducts()
        mViewModel.product.observe(viewLifecycleOwner,{
            when{
                it is StatusResult.Success->{
                    mProductAdapter.setData(it.data!!)
                    showShimmerProduct(false)
                }
                it is StatusResult.Error ->{
                    showShimmerProduct(true)                }
                it is StatusResult.Loading ->{
                    showShimmerProduct(true)                }
            }
        })

        mViewModel.getAllProductsBestSaler()
        mViewModel.productBestSaler.observe(viewLifecycleOwner,{
            when{
                it is StatusResult.Success->{
                    mProductBestSaleAdapter.setData(it.data!!)
                    showShimmerProductbest(false)
                }
                it is StatusResult.Error ->{
                    showShimmerProductbest(true)
                }
                it is StatusResult.Loading ->{
                    showShimmerProductbest(true)                }
            }
        })

        mViewModel.getOfferProd()
        mViewModel.productOffer.observe(viewLifecycleOwner,{
            when{
                it is StatusResult.Success->{

                    it.data?.forEach {
                        imgSliderList.add(SlideModel(it.img, it.offer,ScaleTypes.FIT))
                    }
                    imageSlider.setImageList(imgSliderList)
                }
                it is StatusResult.Error ->{
                    showShimmerProductbest(true)
                }
                it is StatusResult.Loading ->{
                    showShimmerProductbest(true)                }
            }
        })
    }

    private fun setUpRecyclerView() {
        binidng.recyAllProd.apply {
            adapter=mProductAdapter
            layoutManager=LinearLayoutManager(requireActivity(),LinearLayoutManager.HORIZONTAL,false)
        }
        binidng.recyBestsallerProd.apply {
            adapter=mProductBestSaleAdapter
            layoutManager=LinearLayoutManager(requireActivity(),LinearLayoutManager.HORIZONTAL,false)
        }
        binidng.recyCatigoryMainfrag.apply {
            adapter=mCatigoryAdapter
            layoutManager=LinearLayoutManager(requireActivity(),LinearLayoutManager.HORIZONTAL,false)
        }
    }


    fun showShimmerProduct(show:Boolean){
        if (show){

            binidng.recyAllProd.showShimmer()
        }else{
            binidng.recyAllProd.hideShimmer()
        }
    }
    fun showShimmercatigory(show:Boolean){
        if (show){
            binidng.recyCatigoryMainfrag.showShimmer()
        }else {
            binidng.recyCatigoryMainfrag.hideShimmer()
        }
    }
    fun showShimmerProductbest(show:Boolean){
        if (show){
            binidng.recyBestsallerProd.showShimmer()

        }else {
            binidng.recyBestsallerProd.hideShimmer()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}


































