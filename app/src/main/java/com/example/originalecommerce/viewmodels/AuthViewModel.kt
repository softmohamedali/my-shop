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
    private val _isRegister=MutableLiveData<StatusResult<FirebaseUser>>()
    private val _isLogin=MutableLiveData<StatusResult<FirebaseUser>>()
    private val _resetPassword= MutableLiveData<StatusResult<String>>()

    val isRegister:LiveData<StatusResult<FirebaseUser>> =_isRegister
    val isLogin:LiveData<StatusResult<FirebaseUser>> get() = _isLogin
    val resetPassword:LiveData<StatusResult<String>> get() = _resetPassword
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

    fun resetPasswoed(email: String){
        _resetPassword.value=StatusResult.Loading()
        repo.fireBasecSource.resetPassword(email)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    _resetPassword.value=StatusResult.Success()
                }else{
                    _resetPassword.value=StatusResult.Error(it.exception?.message)
                }
            }
    }
}