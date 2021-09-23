package com.example.originalecommerce.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.originalecommerce.models.Product
import com.example.originalecommerce.repo.MainRepo
import com.example.orignal_ecommerce_manger.models.Catigory
import com.example.orignal_ecommerce_manger.util.StatusResult
import com.google.firebase.firestore.DocumentSnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
        private var repo: MainRepo
):ViewModel() {
    // variables
    private var _products=MutableLiveData<StatusResult<MutableList<Product>>> ()
    private var _productsBestSaler=MutableLiveData<StatusResult<MutableList<Product>>> ()
    private var _catigory=MutableLiveData<StatusResult<MutableList<Catigory>>> ()
    private var _productsOffer=MutableLiveData<StatusResult<MutableList<Product>>> ()

    val product:LiveData<StatusResult<MutableList<Product>>> get() = _products
    val productBestSaler:LiveData<StatusResult<MutableList<Product>>> get() = _productsBestSaler
    val catigory:LiveData<StatusResult<MutableList<Catigory>>> get() = _catigory
    val productOffer:LiveData<StatusResult<MutableList<Product>>> get() = _productsOffer
    //Function


    //----------------get all product----------------
    fun getAllProducts(){
        _products.value=StatusResult.Loading()
        repo.fireBasecSource.getAllProduct().addSnapshotListener { value, error ->
            if (value!=null && error==null)
            {
                _products.value=handleGetAllProductResult(value.documents)
            }
            else{
                _products.value=StatusResult.Error(error?.message)
            }
        }
    }
    private fun handleGetAllProductResult(documents: List<DocumentSnapshot>): StatusResult<MutableList<Product>>? {
        val products= mutableListOf<Product>()
        documents.forEach {
            val product=it.toObject(Product::class.java)!!
            products.add(product)
        }
        if (products.isNotEmpty())
        {
            return StatusResult.Success(products)
        }else{
            return StatusResult.Error("No Products Found")
        }
    }
    //---------------------------------------------------------


    //--------------------get best saler product----------------
    fun getAllProductsBestSaler(){
        _productsBestSaler.value=StatusResult.Loading()
        repo.fireBasecSource.getBestSallerprod().addSnapshotListener { value, error ->
            if (value!=null && error==null)
            {
                _productsBestSaler.value=handleGetAllProductResult(value.documents)
            }
            else{
                _productsBestSaler.value=StatusResult.Error(error?.message)
            }
        }
    }
    private fun handleGetProductBestSalerResult(documents: List<DocumentSnapshot>): StatusResult<MutableList<Product>>? {
        val products= mutableListOf<Product>()
        documents.forEach {
            val product=it.toObject(Product::class.java)!!
            products.add(product)
        }
        if (products.isNotEmpty())
        {
            return StatusResult.Success(products)
        }else{
            return StatusResult.Error("No Products Found")
        }
    }

    //---------------------------------------------------------

    //--------------------get best saler product----------------
    fun getAllCatigory(){
        _catigory.value=StatusResult.Loading()
        repo.fireBasecSource.getAllCatigory().addSnapshotListener { value, error ->
            if (value!=null && error==null)
            {
                _catigory.value=handleGetCatigory(value.documents)
            }
            else{
                _catigory.value=StatusResult.Error(error?.message)
            }
        }
    }
    private fun handleGetCatigory (documents: List<DocumentSnapshot>): StatusResult<MutableList<Catigory>> {
        val catigorys= mutableListOf<Catigory>()
        documents.forEach {
            val catigory=it.toObject(Catigory::class.java)!!
            catigorys.add(catigory)
        }
        if (catigorys.isNotEmpty())
        {
            return StatusResult.Success(catigorys)
        }else{
            return StatusResult.Error("No catigors Found")
        }
    }

    //---------------------------------------------------------

    //--------------------get Offer product----------------
    fun getOfferProd(){
        _productsOffer.value=StatusResult.Loading()
        repo.fireBasecSource.getofferProd().addSnapshotListener { value, error ->
            if (value!=null && error==null)
            {
                _productsOffer.value=handleGetOfferProd(value.documents)
            }
            else{
                _productsOffer.value=StatusResult.Error(error?.message)
            }
        }
    }
    private fun handleGetOfferProd (documents: List<DocumentSnapshot>): StatusResult<MutableList<Product>>? {
        val offerProd= mutableListOf<Product>()
        documents.forEach {
            val prod=it.toObject(Product::class.java)!!
            offerProd.add(prod)
        }
        if (offerProd.isNotEmpty())
        {
            return StatusResult.Success(offerProd)
        }else{
            return StatusResult.Error("No product Found")
        }
    }

    //---------------------------------------------------------
}