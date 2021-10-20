package com.example.originalecommerce.data.remote

import com.example.originalecommerce.models.Paymnts
import com.example.originalecommerce.utils.Constants
import com.example.orignal_ecommerce_manger.models.Catigory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import java.util.*
import javax.inject.Inject


class FireBasecSource @Inject constructor(
    private var firebaseAuth: FirebaseAuth,
    private var firestore: FirebaseFirestore,
    private var storage: FirebaseStorage,
){

    //Auth

    fun logIn(email:String,pass:String)=
        firebaseAuth.signInWithEmailAndPassword(email,pass)

    fun getCurrentUser()=firebaseAuth.currentUser

    fun logOut()=firebaseAuth.signOut()

    fun register(email:String,pass:String)=firebaseAuth.createUserWithEmailAndPassword(email,pass)

    fun resetPassword(email: String)=firebaseAuth.sendPasswordResetEmail(email)

    //operation

    fun getAllProduct()=firestore.collection(Constants.PRODUCT_COLLLECTION)

    fun getProductWithType(type:String)=firestore.collection(Constants.PRODUCT_COLLLECTION)
        .whereEqualTo(Constants.PRODUCT_TYPE,type)

    fun getProductWithCatigory(catigory:String)=firestore.collection(Constants.PRODUCT_COLLLECTION)
            .whereEqualTo(Constants.PRODUCT_CATIGORY,catigory)

    fun getAllBestProduct()=firestore.collection(Constants.PRODUCT_COLLLECTION)
        .whereEqualTo(Constants.PRODUCT_ISBESTSALLER,"true")

    fun getSortedProduct()=firestore.collection(Constants.PRODUCT_COLLLECTION)
        .orderBy(Constants.PRODUCT_TIME, Query.Direction.DESCENDING).limit(3)

    fun getAllOfferProduct()=firestore.collection(Constants.PRODUCT_COLLLECTION)
        .whereEqualTo(Constants.PRODUCT_ISOFFR,"true")

    fun getAllCatigory()=firestore.collection(Constants.CATIGORY_COLLECTION)

    fun search(name:String)=firestore.collection(Constants.PRODUCT_COLLLECTION)
        .orderBy(Constants.PRODUCT_NAME)
        .startAt(name.trim())
        .endAt(name.trim()+"\uf8ff")
        .get()


    fun uploadPayments()=firestore.collection(Constants.PAYMENTS_COLLECTION)
        .document()

    fun getAllPayments()=firestore
        .collection(Constants.PAYMENTS_COLLECTION)


}