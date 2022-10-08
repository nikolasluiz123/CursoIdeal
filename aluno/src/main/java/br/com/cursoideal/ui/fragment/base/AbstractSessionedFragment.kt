package br.com.cursoideal.ui.fragment.base

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import br.com.cursoideal.NavGraphDirections
import br.com.cursoideal.R
import br.com.cursoideal.ui.viewmodel.ComponentsViewControll

abstract class AbstractSessionedFragment : AbstractAuthenticableFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verifyLoggedUser()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appStateViewModel.hasComponents = ComponentsViewControll()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Achar uma solução melhor posteriormente, para não ficar sobrescrevendo
        // Algo que o próprio navigation adiciona por padrão.
        activity?.findViewById<Toolbar>(R.id.toolbar)?.let {
            it.navigationIcon = ResourcesCompat.getDrawable(
                resources,
                R.drawable.ic_navigation,
                context.theme
            )
        }
    }

    private fun verifyLoggedUser() {
        if (authenticationViewModel.isNotLogged()) {
            navController.navigate(NavGraphDirections.actionGlobalLoginFragment())
        }
    }
}