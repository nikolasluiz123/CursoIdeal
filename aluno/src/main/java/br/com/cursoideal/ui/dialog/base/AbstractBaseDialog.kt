package br.com.cursoideal.ui.dialog.base

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

abstract class AbstractBaseDialog : DialogFragment() {

    abstract val dialogTag: String

    open fun show(manager: FragmentManager) {
        super.show(manager, dialogTag)
    }
}