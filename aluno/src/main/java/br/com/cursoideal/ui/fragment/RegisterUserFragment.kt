package br.com.cursoideal.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.cursoideal.R
import br.com.cursoideal.databinding.FragmentRegisterUserBinding
import br.com.cursoideal.extensions.showSnackBar
import br.com.cursoideal.model.UserAuthentication
import br.com.cursoideal.transferobject.TOUser
import br.com.cursoideal.ui.fragment.base.AbstractAuthenticableFragment

class RegisterUserFragment : AbstractAuthenticableFragment() {

    private var _binding: FragmentRegisterUserBinding? = null
    private val binding get() = _binding!!

    private val toUser by lazy { TOUser() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRegisterUserBinding.inflate(inflater, container, false)
        binding.toUser = toUser

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureRegisterUserButton()
    }

    private fun configureRegisterUserButton() {
        binding.registerUserRegisterButton.setOnClickListener {
            toUser.getUserAuthentication()?.let { user ->
                if (validate(user)) {
                    authenticationViewModel.save(user).observe(viewLifecycleOwner) { resource ->
                        if (resource.data) {
                            view?.showSnackBar(getString(R.string.message_success_register_user))
                            navController.popBackStack()
                        } else {
                            val errorMessage = resource.error
                                ?: getString(R.string.generic_error_message_register_user)
                            view?.showSnackBar(errorMessage)
                        }
                    }
                }
            }
        }
    }

    private fun validate(user: UserAuthentication): Boolean {
        var valid = true

        clearErrors()

        if (user.email.isBlank()) {
            binding.registerUserInputLayoutEmail.error = getString(R.string.email_required)
            valid = false
        }

        if (user.password.isBlank()) {
            binding.registerUserInputLayoutPassword.error = getString(R.string.password_required)
            valid = false
        }

        return valid
    }

    private fun clearErrors() {
        binding.registerUserInputLayoutEmail.error = null
        binding.registerUserInputLayoutPassword.error = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}