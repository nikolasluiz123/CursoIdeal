package br.com.cursoideal.ui.fragment.base

import br.com.cursoideal.ui.viewmodel.AuthenticationViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

abstract class AbstractAuthenticableFragment : AbstractBaseFragment() {

    protected val authenticationViewModel: AuthenticationViewModel by sharedViewModel()
}