package br.com.cursoideal.ui.fragment.base

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import br.com.cursoideal.NavGraphDirections
import br.com.cursoideal.R

abstract class AbstractSessionedFragment : AbstractAuthenticableFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verifyLoggedUser()
    }

    private fun verifyLoggedUser() {
        if (authenticationViewModel.isNotLogged()) {
            navController.navigate(NavGraphDirections.actionGlobalLoginFragment())
        }
    }
}