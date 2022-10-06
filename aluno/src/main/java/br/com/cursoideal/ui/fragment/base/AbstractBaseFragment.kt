package br.com.cursoideal.ui.fragment.base

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.cursoideal.ui.viewmodel.AppStateViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

abstract class AbstractBaseFragment : Fragment() {

    protected val appStateViewModel: AppStateViewModel by sharedViewModel()
    protected val navController by lazy { findNavController() }
}