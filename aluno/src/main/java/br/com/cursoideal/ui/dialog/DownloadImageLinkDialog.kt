package br.com.cursoideal.ui.dialog

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import br.com.cursoideal.R
import br.com.cursoideal.databinding.DownloadImageLinkDialogBinding
import br.com.cursoideal.ui.dialog.base.AbstractFullScreenDialog
import coil.imageLoader
import coil.request.ImageRequest

class DownloadImageLinkDialog(private val callback: (drawable: Drawable?) -> Unit) : AbstractFullScreenDialog() {

    override val dialogTag: String = "DownloadImageLinkDialog"

    private var _binding: DownloadImageLinkDialogBinding? = null
    private val binding get() = _binding!!

    private var image: Drawable? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = DownloadImageLinkDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonPreview.setOnClickListener {
            context?.let { context ->
                val request = ImageRequest.Builder(context)
                    .data(binding.inputLinkImage.text.toString())
                    .target(
                        onStart = {
                            binding.downloadImageLinkLoading.visibility = View.VISIBLE
                        },
                        onSuccess = { drawable ->
                            binding.downloadImageLinkLoading.visibility = View.GONE
                            binding.imageviewPhoto.setImageDrawable(drawable)
                            image = drawable
                        },
                        onError = {
                            binding.downloadImageLinkLoading.visibility = View.GONE
                        }
                    ).build()

                context.imageLoader.enqueue(request)
            }
        }

        binding.downloadImageLinkDialogToolbar.apply {
            setNavigationOnClickListener { dismiss() }
            setOnMenuItemClickListener {
                callback(image)
                dismiss()
                true
            }
        }
    }
}