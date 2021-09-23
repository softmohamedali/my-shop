package com.example.originalecommerce.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.originalecommerce.repo.MainRepo
import com.example.orignal_ecommerce_manger.util.StatusResult
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class AuthViewModel @Inject constructor(
        private var repo: MainRepo
):ViewModel() {

    //------------------variables---------------------------------------
    private var _isRegister=MutableLiveData<StatusResult<FirebaseUser>>()
    private var _isLogin=MutableLiveData<StatusResult<FirebaseUser>>()

    var isRegister:LiveData<StatusResult<FirebaseUser>> =_isRegister
    val isLogin:LiveData<StatusResult<FirebaseUser>> get() = _isLogin
    var currentUser=repo.fireBasecSource.getCurrentUser()

    //------------------fun--------------------------------------------
    fun register(email:String,pass:String,name:String){
        _isRegister.value=StatusResult.Loading()
        repo.fireBasecSource.register(email, pass).addOnCompleteListener {
            if (it.isSuccessful)
            {
                _isRegister.value=StatusResult.Success(repo.fireBasecSource.getCurrentUser())
            }
            else{
                _isRegister.value=StatusResult.Error(it.exception?.message)
            }
        }
    }

    fun logIn(email: String,pass: String){
        _isLogin.value=StatusResult.Loading()
        repo.fireBasecSource.logIn(email, pass).addOnCompleteListener {
            if (it.isSuccessful)
            {
                _isLogin.value=StatusResult.Success(repo.fireBasecSource.getCurrentUser())
            }
            else{
                _isLogin.value=StatusResult.Error(it.exception?.message)
            }
        }
    }

    fun logOut(){
        repo.fireBasecSource.logOut()
    }
}