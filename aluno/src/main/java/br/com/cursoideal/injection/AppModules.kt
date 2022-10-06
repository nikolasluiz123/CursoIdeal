package br.com.cursoideal.injection

import br.com.cursoideal.repository.FirebaseAuthenticationRepository
import br.com.cursoideal.ui.viewmodel.AppStateViewModel
import br.com.cursoideal.ui.viewmodel.AuthenticationViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { AppStateViewModel() }
    viewModel { AuthenticationViewModel(get()) }
}

val repositoryModule = module {
    single { FirebaseAuthenticationRepository(get(), get(), get()) }
}

val firebaseModule = module {
    single { Firebase.auth }
    single { Firebase.firestore }
    single { Firebase.storage }
}