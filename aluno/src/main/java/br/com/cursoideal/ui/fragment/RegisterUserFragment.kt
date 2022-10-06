package br.com.cursoideal.ui.fragment

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import br.com.cursoideal.R
import br.com.cursoideal.databinding.FragmentRegisterUserBinding
import br.com.cursoideal.extensions.showSnackBar
import br.com.cursoideal.extensions.toByteArray
import br.com.cursoideal.transferobject.TOUser
import br.com.cursoideal.ui.fragment.base.AbstractAuthenticableFragment
import br.com.cursoideal.ui.dialog.bottomsheet.UploadImageBottomSheetDialog
import br.com.cursoideal.ui.viewmodel.ComponentsViewControll
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

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

        appStateViewModel.hasComponents = ComponentsViewControll()
        configureRegisterUserButton()
        configureEditPhoto()
    }

    private fun configureRegisterUserButton() {
        binding.registerUserRegisterButton.setOnClickListener {
            if (validate(toUser)) {

                toUser.byteArrayPhoto = binding.imageViewUserPhoto.toByteArray()

                authenticationViewModel.save(toUser).observe(viewLifecycleOwner) { resource ->
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

    private fun validate(toUser: TOUser): Boolean {
        var valid = true

        clearErrors()

        if (toUser.name.value?.isBlank() != false) {
            binding.registerUserInputLayoutNome.error = getString(R.string.name_required)
            valid = false
        }

        if (toUser.email.value?.isBlank() != false) {
            binding.registerUserInputLayoutEmail.error = getString(R.string.email_required)
            valid = false
        }

        if (toUser.password.value?.isBlank() != false) {
            binding.registerUserInputLayoutPassword.error = getString(R.string.password_required)
            valid = false
        }

        return valid
    }

    private fun clearErrors() {
        binding.registerUserInputLayoutEmail.error = null
        binding.registerUserInputLayoutPassword.error = null
    }

    private fun configureEditPhoto() {
        binding.registerUserPhoto.setOnClickListener {
            activity?.let { activity ->
                UploadImageBottomSheetDialog(binding.imageViewUserPhoto).show(
                    activity.supportFragmentManager,
                    UploadImageBottomSheetDialog.TAG
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}