package br.com.cursoideal.extensions

import br.com.cursoideal.R
import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.executeRequiredValidation(): Boolean {
    if (this.editText?.text.isNullOrBlank()) {
        this.error = resources.getString(R.string.required_message, this.hint)
        return false
    }

    this.error = null
    return true
}