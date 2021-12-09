package com.example.originalecommerce.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.*
import androidx.room.withTransaction
import com.example.originalecommerce.R
import com.example.originalecommerce.data.local.entitys.*
import com.example.originalecommerce.models.Paymnts
import com.example.originalecommerce.models.Product
import com.example.originalecommerce.repo.MainRepo
import com.example.originalecommerce.utils.Constants
import com.example.orignal_ecommerce_manger.models.Catigory
import com.example.orignal_ecommerce_manger.util.StatusResult
import com.google.firebase.firestore.DocumentSnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application,
    private var repo: MainRepo
) : AndroidViewModel(application) {


    // DataBase
    val readBestsaller = repo.dataBaseSource.getDao().getAllBestProd().asLiveData()
    val readOffer = repo.dataBaseSource.getDao().getAllOfferProd().asLiveData()
    val readCatigory = repo.dataBaseSource.getDao().getAllCatigory().asLiveData()
    val readFavEntity = repo.dataBaseSource.getDao().getAllOFavProd().asLiveData()
    val readOrders=repo.dataBaseSource.getDao().getAllOrders().asLiveData()
    val readNotification=repo.dataBaseSource.getDao().getAllNotification().asLiveData()


    fun insertBestProd(prods: BestSallerEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.dataBaseSource.getDao().insertBestProds(prods)
        }
    }

    fun insertOfferProd(prods: OfferEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.dataBaseSource.getDao().insertOfferProds(prods)
        }
    }

    fun insertCatigory(catigorys: CatigoryEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.dataBaseSource.getDao().insertCatigory(catigorys)
        }
    }

    fun insertFav(favEntity: FavEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.dataBaseSource.getDao().insertFavProds(favEntity)
        }
    }

    fun insertNotification(notificationEntity: NotificationEntity)
    {
        viewModelScope.launch(Dispatchers.IO) {
            repo.dataBaseSource.getDao().insertNotific(notificationEntity)
        }
    }

    fun deleteFav(favEntity: FavEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.dataBaseSource.getDao().deleteFavProds(favEntity)
        }
    }

    fun insertOrder(orderEntity: OrderEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.dataBaseSource.getDao().insertOrder(orderEntity)
        }
    }

    fun deleteOrder(orderEntity: OrderEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.dataBaseSource.getDao().deleteOrder(orderEntity)
        }
    }

    fun deleteAllOrder() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.dataBaseSource.getDao().deleteAllOrders()
        }
    }

    fun updateOrder(orderEntity: OrderEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.dataBaseSource.getDao().UpdateOrder(orderEntity)
        }
    }


    // Remote
    private var _products = MutableLiveData<StatusResult<MutableList<Product>>>()
    private var _catigory = MutableLiveData<StatusResult<MutableList<Catigory>>>()
    private var _prodWithCatigory = MutableLiveData<StatusResult<MutableList<Product>>>()
    private var _bestrPoducts = MutableLiveData<StatusResult<MutableList<Product>>>()
    private var _offerProducts = MutableLiveData<StatusResult<MutableList<Product>>>()
    private var _search = MutableLiveData<StatusResult<MutableList<Product>>>()
    private var _paymntsIsUpload=MutableLiveData<StatusResult<MutableList<OrderEntity>>>()
    private var _myPayments=MutableLiveData<StatusResult<MutableList<Paymnts>>>()
    private var _productType=MutableLiveData<StatusResult<MutableList<Product>>>()

    val product: LiveData<StatusResult<MutableList<Product>>> get() = _products
    val catigory: LiveData<StatusResult<MutableList<Catigory>>> get() = _catigory
    val prodWithCatigory: LiveData<StatusResult<MutableList<Product>>> get() = _prodWithCatigory
    val bestProducts: LiveData<StatusResult<MutableList<Product>>> get() = _bestrPoducts
    val offerProducts: LiveData<StatusResult<MutableList<Product>>> get() = _offerProducts
    val search:LiveData<StatusResult<MutableList<Product>>> get() = _search
    val paymntsIsUpload:LiveData<StatusResult<MutableList<OrderEntity>>> get()=_paymntsIsUpload
    val myPaymnts:LiveData<StatusResult<MutableList<Paymnts>>> get()=_myPayments
    val productType: LiveData<StatusResult<MutableList<Product>>> get() = _productType
    //----------------get product----------------
    fun getAllBestProducts() {
        _bestrPoducts.value = StatusResult.Loading()
        if (hasInternetConnection()) {
            repo.fireBasecSource.getAllBestProduct().addSnapshotListener { value, error ->
                if (value != null && error == null) {
                    val response = handleGetProductResult(value.documents)
                    _bestrPoducts.value = response

                    if (response.data != null) {
                        insertBestProd(BestSallerEntity(response.data!!))
                    }
                } else {
                    _bestrPoducts.value = StatusResult.Error(error?.message)
                }
            }
        } else {
            _bestrPoducts.value = StatusResult.Error("No Internet Connection")
        }

    }


    fun getProductType(type:String)
    {
        _productType.value = StatusResult.Loading()
        if (hasInternetConnection()) {
            if (type.isEmpty()){
                repo.fireBasecSource.getAllProduct().addSnapshotListener { value, error ->
                    if (value != null && error == null) {
                        val response = handleGetProductResult(value.documents)
                        _productType.value = response

                        if (response.data != null) {
                            insertBestProd(BestSallerEntity(response.data!!))
                        }
                    } else {
                        _productType.value = StatusResult.Error(error?.message)
                    }
                }
            }else if(type==Constants.PRODUCT_TIME){
                repo.fireBasecSource.getSortedProduct().addSnapshotListener { value, error ->
                    if (value != null && error == null) {
                        val response = handleGetProductResult(value.documents)
                        _productType.value = response

                        if (response.data != null) {
                            insertBestProd(BestSallerEntity(response.data!!))
                        }
                    } else {
                        _productType.value = StatusResult.Error(error?.message)
                    }
                }
            } else{
                repo.fireBasecSource.getProductWithType(type).addSnapshotListener { value, error ->
                    if (value != null && error == null) {
                        val response = handleGetProductResult(value.documents)
                        _productType.value = response

                        if (response.data != null) {
                            insertBestProd(BestSallerEntity(response.data!!))
                        }
                    } else {
                        _productType.value = StatusResult.Error(error?.message)
                    }
                }
            }
        } else {
            _productType.value = StatusResult.Error("No Internet Connection")
        }

    }

    private fun handleGetProductResult(documents: List<DocumentSnapshot>): StatusResult<MutableList<Product>> {
        val products = mutableListOf<Product>()
        documents.forEach {
            val product = it.toObject(Product::class.java)!!
            products.add(product)
        }
        if (products.isNotEmpty()) {
            return StatusResult.Success(products)
        } else {
            return StatusResult.Error("No Products Found")
        }
    }

    fun getAllOfferProducts() {
        _offerProducts.value = StatusResult.Loading()
        if (hasInternetConnection()) {
            repo.fireBasecSource.getAllOfferProduct().addSnapshotListener { value, error ->
                if (value != null && error == null) {
                    val response = handleGetProductResult(value.documents)
                    _offerProducts.value = response

                    if (response.data != null) {
                        insertOfferProd(OfferEntity(response.data!!))
                    }
                } else {
                    _offerProducts.value = StatusResult.Error(error?.message)
                }
            }
        } else {
            _offerProducts.value = StatusResult.Error("No Internet Connection")
        }

    }
    fun getProductWithCatigory(cati: String) {
        _prodWithCatigory.value = StatusResult.Loading()
        if (hasInternetConnection()) {
            repo.fireBasecSource.getProductWithCatigory(cati).addSnapshotListener { value, error ->
                if (value != null && error == null) {
                    _prodWithCatigory.value = handlegetProductWithCatigory(value.documents)
                } else {
                    _prodWithCatigory.value = StatusResult.Error("${error?.message}")
                }
            }
        } else {
            _prodWithCatigory.value = StatusResult.Error("No Internet Connection")
        }
    }

    private fun handlegetProductWithCatigory(documents: List<DocumentSnapshot>): StatusResult<MutableList<Product>>? {
        val prod = mutableListOf<Product>()
        documents.forEach {
            prod.add(it.toObject(Product::class.java)!!)
        }
        if (prod.isEmpty()) {
            return StatusResult.Error("No Product found")
        } else {
            return StatusResult.Success(prod)
        }
    }

    fun getProductSearch(name: String) {
        _search.value = StatusResult.Loading()
        if (hasInternetConnection()) {
            repo.fireBasecSource.search(name).addOnSuccessListener {
                if (!it.documents.isEmpty())
                {
                    _search.value = handlegetProductWithCatigory(it.documents)
                }else{
                    _search.value = StatusResult.Error("No Product Found")
                }

            }.addOnFailureListener {
                _search.value = StatusResult.Error(it.message)
            }
        } else {
            _search.value = StatusResult.Error("No Internet Connection")
        }
//            .addOnFailureListener {
//                searchProduct.value=StatusResult.Error(it.message)
//            }
//            .addOnSuccessListener { value, error ->
//                if (value != null && error == null) {
//                    _search.value = handlegetProductWithCatigory(value.documents)
//                } else {
//                    _search.value = StatusResult.Error("${error?.message}")
//                }
//            }
    }
    //-----------------------------------------------------

    //----------------get all catigory----------------
    fun getAllCatigory() {
        _catigory.value = StatusResult.Loading()
        repo.fireBasecSource.getAllCatigory().addSnapshotListener { value, error ->
            if (value != null && error == null) {
                val response = handleGetCatigory(value.documents)
                _catigory.value = response

                if (response.data != null) {
                    insertCatigory(CatigoryEntity(response.data!!))
                    Log.d("del", "cadhing cat ")
                }
            } else {
                _catigory.value = StatusResult.Error(error?.message)
            }
        }
    }


    private fun handleGetCatigory(documents: List<DocumentSnapshot>): StatusResult<MutableList<Catigory>> {
        val catigorys = mutableListOf<Catigory>()
        documents.forEach {
            val catigory = it.toObject(Catigory::class.java)!!
            catigorys.add(catigory)
        }
        if (catigorys.isNotEmpty()) {
            return StatusResult.Success(catigorys)
        } else {
            return StatusResult.Error("No catigors Found")
        }
    }

    //---------------------------------------------------------


    //-------------------------Payment-------------------------
    fun getAllPayments(){
        _myPayments.value=StatusResult.Loading()
        if (hasInternetConnection())
        {
            repo.fireBasecSource.getAllPayments().addSnapshotListener { value, error ->
                if (value!=null&&error==null)
                {
                    val myValue=value.documents
                    _myPayments.value=handleGetPayments(myValue)
                }else{
                    _myPayments.value=StatusResult.Error("${error?.message}")
                }
            }
        }else{
            _myPayments.value=StatusResult.Error("No Internet Connection")
        }
    }

    private fun handleGetPayments(myValue: List<DocumentSnapshot>): StatusResult<MutableList<Paymnts>>? {
        val myList= mutableListOf<Paymnts>()
        myValue.forEach {
            myList.add(it.toObject(Paymnts::class.java)!!)
        }
        if (myList.isNotEmpty()){
            return StatusResult.Success(myList)
        }else{
            return StatusResult.Error("No Data Found")
        }
    }

    fun uploadPayments(paymnts: Paymnts){
        val ref=repo.fireBasecSource.uploadPayments()
        val id=repo.fireBasecSource.getCurrentUser()?.uid
        paymnts.id=id
        _paymntsIsUpload.value=StatusResult.Loading()
        ref.set(paymnts)
            .addOnCompleteListener {
                if (it.isSuccessful)
                {
                    _paymntsIsUpload.value=StatusResult.Success()
                }
                else{
                    _paymntsIsUpload.value=StatusResult.Error(it.exception?.message)
                }
            }
    }
    //------------------------------------------------------------------------



    private fun hasInternetConnection(): Boolean {
        val connectivityManger = getApplication<Application>()
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netWorkActive = connectivityManger.activeNetwork ?: return false
        val networkCapability =
            connectivityManger.getNetworkCapabilities(netWorkActive) ?: return false
        when {
            networkCapability.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
            networkCapability.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            networkCapability.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
            else -> return false
        }

    }


}








































////----------------get all product----------------
//fun getAllProducts(){
//    _products.value=StatusResult.Loading()
//    if(hasInternetConnection())
//    {
//        repo.fireBasecSource.getAllProduct().addSnapshotListener { value, error ->
//            if (value!=null && error==null)
//            {
//                val response=handleGetAllProductResult(value.documents)
//                _products.value=response
//
//                if (response.data!=null){
//                    cashingData(response.data!!)
//                }
//            }
//            else{
//                _products.value=StatusResult.Error(error?.message)
//            }
//        }
//    }
//    else{
//        _products.value=StatusResult.Error("No Internet Connection")
//    }
//
//}
//
//private fun cashingData(data: MutableList<Product>) {
//    data.forEach {
//        insertProduct(it)
//    }
//}
//
//private fun handleGetAllProductResult(documents: List<DocumentSnapshot>): StatusResult<MutableList<Product>>{
//    val products= mutableListOf<Product>()
//    documents.forEach {
//        val product=it.toObject(Product::class.java)!!
//        products.add(product)
//        Log.d("del","prod")
//    }
//    if (products.isNotEmpty())
//    {
//        return StatusResult.Success(products)
//    }else{
//        return StatusResult.Error("No Products Found")
//    }
//}
////---------------------------------------------------------