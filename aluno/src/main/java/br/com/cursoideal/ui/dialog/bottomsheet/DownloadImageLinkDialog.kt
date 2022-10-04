package br.com.cursoideal.ui.dialog.bottomsheet

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import br.com.cursoideal.R
import br.com.cursoideal.databinding.DownloadImageLinkDialogBinding
import coil.imageLoader
import coil.request.ImageRequest

class DownloadImageLinkDialog(private val callback: (drawable: Drawable?) -> Unit) : DialogFragment() {

    private var _binding: DownloadImageLinkDialogBinding? = null
    private val binding get() = _binding!!

    private var image: Drawable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NORMAL, R.style.AppTheme_FullScreenDialog)
    }

    override fun onStart() {
        super.onStart()

        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.MATCH_PARENT
        dialog?.window?.setLayout(width, height)
    }

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
            setTitle(R.string.label_title_download_imagem_link_dialog)
            inflateMenu(R.menu.menu_download_image_link_dialog)
            setOnMenuItemClickListener {
                callback(image)
                dismiss()
                true
            }
        }
    }
}