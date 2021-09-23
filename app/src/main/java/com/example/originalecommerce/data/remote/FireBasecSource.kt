package com.example.originalecommerce.data.remote

import com.example.originalecommerce.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*
import javax.inject.Inject


class FireBasecSource @Inject constructor(
    private var firebaseAuth: FirebaseAuth,
    private var firestore: FirebaseFirestore,
    private var storage: FirebaseStorage
){

    //Auth

    fun logIn(email:String,pass:String)=
        firebaseAuth.signInWithEmailAndPassword(email,pass)

    fun getCurrentUser()=firebaseAuth.currentUser

    fun logOut()=firebaseAuth.signOut()

    fun register(email:String,pass:String)=firebaseAuth.createUserWithEmailAndPassword(email,pass)


    //operation

    fun getAllProduct()=firestore.collection(Constants.PRODUCT_COLLLECTION)

    fun getBestSallerprod()=firestore.collection(Constants.PRODUCT_COLLLECTION)
            .whereEqualTo(Constants.PRODUCT_ISBESTSALLER,"true")

    fun getofferProd()=firestore.collection(Constants.PRODUCT_COLLLECTION)
        .whereEqualTo(Constants.PRODUCT_ISOFFR,"true")

    fun getAllCatigory()=firestore.collection(Constants.CATIGORY_COLLECTION)




}