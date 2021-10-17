package com.example.originalecommerce.ui.body

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.originalecommerce.R
import com.example.originalecommerce.adapters.CatigoryItemAdapter
import com.example.originalecommerce.adapters.ProductItemAdapter
import com.example.originalecommerce.databinding.FragmentMainBodyBinding
import com.example.originalecommerce.models.Product
import com.example.originalecommerce.utils.Constants
import com.example.originalecommerce.viewmodels.MainViewModel
import com.example.orignal_ecommerce_manger.models.Catigory
import com.example.orignal_ecommerce_manger.util.StatusResult
import com.example.orignal_ecommerce_manger.util.observerOnce
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainBodyFragment : Fragment() {
    private var _binding: FragmentMainBodyBinding? = null
    private val binidng get() = _binding!!

    private val mViewModel by viewModels<MainViewModel>()

    private val mProductBestSaleAdapter by lazy {
        ProductItemAdapter(mViewModel, requireActivity(),Constants.MAIN_CONTAINER)
    }
    private val mCatigoryAdapter by lazy { CatigoryItemAdapter() }


    private lateinit var imgSliderList: ArrayList<SlideModel>
    private lateinit var imageSlider: ImageSlider

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBodyBinding.inflate(layoutInflater)
        setUpView()
        return binidng.root
    }

    fun setUpView() {
        setUpRecyclerView()
        readLocalcatigory()
        readLocalBestProds()
        readLocalOfferProds()
        binidng.imgMyordersMainfrag.setOnClickListener {
            findNavController().navigate(R.id.action_mainBodyFragment_to_showMyOrderFragment)
        }
        binidng.ibtnNotifiMainfrag.setOnClickListener {
            findNavController().navigate(R.id.action_mainBodyFragment_to_notificationFragment)
        }
        binidng.btnSearchMainfrag.setOnClickListener {
            findNavController().navigate(R.id.action_mainBodyFragment_to_searchFragment)
        }
    }

    private fun readLocalBestProds() {
        mViewModel.getAllBestProducts()
        mViewModel.readBestsaller.observerOnce(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                mProductBestSaleAdapter.setData((it.get(0).bestProduct))
            } else {
                getRemoteDataBestProds()
            }
        })
    }

    private fun getRemoteDataBestProds() {
        mViewModel.bestProducts.observe(viewLifecycleOwner, { it ->
            when {
                it is StatusResult.Success -> {
                    mProductBestSaleAdapter.setData(it.data!!)
                    showShimmerProductbest(false)
                }
                it is StatusResult.Error -> {
                    showShimmerProductbest(false)
                    mViewModel.readBestsaller.observerOnce(viewLifecycleOwner, {
                        if (it.isNotEmpty()) {
                            mProductBestSaleAdapter.setData((it.get(0).bestProduct))
                        }
                    })
                }
                it is StatusResult.Loading -> {
                    showShimmerProductbest(true)
                }
            }
        })
    }

    private fun readLocalOfferProds() {
        mViewModel.getAllOfferProducts()
        mViewModel.readOffer.observerOnce(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                setSliderOfferImg(it[0].offerProducts)
            } else {
                getRemoteDataOfferProds()
            }
        })
    }

    private fun getRemoteDataOfferProds() {
        mViewModel.offerProducts.observe(viewLifecycleOwner, { it ->
            when {
                it is StatusResult.Success -> {
                    setSliderOfferImg(it.data!!)
                }
                it is StatusResult.Error -> {
                    mViewModel.readOffer.observerOnce(viewLifecycleOwner, {
                        if (it.isNotEmpty()) {
                            setSliderOfferImg(it[0].offerProducts)
                        }
                    })
                }
                it is StatusResult.Loading -> {
                }
            }
        })
    }

    private fun readLocalcatigory() {
        Log.d("warf", "read local")
        mViewModel.getAllCatigory()
        mViewModel.readCatigory.observerOnce(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                mCatigoryAdapter.setData((it.get(0).catigorys))
            } else {
                getRemoteDataCatigory()
            }
        })
    }


    private fun getRemoteDataCatigory() {
        Log.d("warf", "read remote")
        mViewModel.catigory.observe(viewLifecycleOwner, { it ->
            when {
                it is StatusResult.Success -> {
                    mCatigoryAdapter.setData(it.data!!)
                    showShimmercatigory(false)
                }
                it is StatusResult.Error -> {
                    showShimmercatigory(false)
                    mViewModel.readCatigory.observerOnce(viewLifecycleOwner, {
                        if (it.isNotEmpty()) {
                            mCatigoryAdapter.setData((it.get(0).catigorys))
                        }
                    })
                }
                it is StatusResult.Loading -> {
                    showShimmercatigory(true)
                }
            }
        })
    }


    private fun setSliderOfferImg(list: MutableList<Product>) {
        imgSliderList = ArrayList<SlideModel>() // Create image list
        imageSlider = binidng.imgMainfragSlideroffers

        imageSlider.setItemClickListener(object : ItemClickListener {
            override fun onItemSelected(position: Int) {
                findNavController().navigate(R.id.action_mainBodyFragment_to_productCatigoryFragment)
            }
        })
        list.forEach {
            imgSliderList.add(SlideModel(it.img, "Offer rech to ${it.offer} %", ScaleTypes.FIT))
        }
        imageSlider.setImageList(imgSliderList)

    }

    private fun setUpRecyclerView() {
        binidng.recyBestsallerProd.apply {
            adapter = mProductBestSaleAdapter
            layoutManager =
                GridLayoutManager(requireActivity(),2)
        }
        binidng.recyCatigoryMainfrag.apply {
            adapter = mCatigoryAdapter
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    fun showShimmercatigory(show: Boolean) {
        if (show) {
            binidng.recyCatigoryMainfrag.showShimmer()
        } else {
            binidng.recyCatigoryMainfrag.hideShimmer()
        }
    }

    fun showShimmerProductbest(show: Boolean) {
        if (show) {
            binidng.recyBestsallerProd.showShimmer()

        } else {
            binidng.recyBestsallerProd.hideShimmer()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}


















