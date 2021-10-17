package com.example.originalecommerce.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.example.originalecommerce.R
import com.example.originalecommerce.databinding.FragmentLogInBinding
import com.example.originalecommerce.viewmodels.AuthViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        FirebaseMessaging.getInstance().subscribeToTopic("/topics/myTopic")

    }
}