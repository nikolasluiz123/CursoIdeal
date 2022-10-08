package br.com.cursoideal.ui.dialog.bottomsheet

import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import br.com.cursoideal.databinding.UploadImageBottomSheetDialogBinding
import br.com.cursoideal.ui.dialog.DownloadImageLinkDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.File
import java.util.*


class UploadImageBottomSheetDialog(private val imageView: ImageView) : BottomSheetDialogFragment() {

    private var _binding: UploadImageBottomSheetDialogBinding? = null
    private val binding get() = _binding!!

    private lateinit var takeImageCameraLauncher: ActivityResultLauncher<Uri>
    private lateinit var takeImageGalleryLauncher: ActivityResultLauncher<Intent>
    private lateinit var requestCameraPermissionLauncher: ActivityResultLauncher<String>
    private lateinit var requestGalleryPermissionLauncher: ActivityResultLauncher<String>
    private var imageUri: Uri? = null

    companion object {
        const val TAG = "UploadImageBottomSheetDialog"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        imageUri = createImageUri()

        registerTakeImageCameraLauncher()
        registerTakeImageGalleryLauncher()
        requestCameraPermissionLauncher = registerExecutionOnPermissionGranted(::openCamera)
        requestGalleryPermissionLauncher = registerExecutionOnPermissionGranted(::openGallery)
    }

    private fun registerExecutionOnPermissionGranted(onPermissionGranted: () -> Unit) =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                onPermissionGranted()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = UploadImageBottomSheetDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.uploadCamera.setOnClickListener {
            requestCameraPermissionLauncher.launch(CAMERA)
        }

        binding.uploadGallery.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            takeImageGalleryLauncher.launch(intent)
        }

        binding.uploadLink.setOnClickListener {
            activity?.let { activity ->
                DownloadImageLinkDialog { drawable ->
                    imageView.setImageDrawable(drawable)
                }.show(activity.supportFragmentManager)

                dismiss()
            }
        }
    }

    private fun registerTakeImageCameraLauncher() {
        takeImageCameraLauncher =
            registerForActivityResult(ActivityResultContracts.TakePicture()) { result ->
                if (result) {
                    imageView.setImageURI(null)
                    imageView.setImageURI(imageUri)
                    dismiss()
                }
            }
    }

    private fun registerTakeImageGalleryLauncher() {
        takeImageGalleryLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    result.data?.let {
                        imageView.setImageURI(it.data)
                        dismiss()
                    }
                }
            }
    }

    private fun createImageUri(): Uri? = activity?.let { activity ->
        val time = Date().time

        val imageFile = File(
            activity.applicationContext.filesDir,
            "user_photo_${time}.jpg"
        )

        FileProvider.getUriForFile(
            activity.applicationContext,
            "br.com.cursoideal.fileProvider",
            imageFile
        )
    }

    private fun openCamera() {
        val context = requireContext()

        val cameraPermissionNotGranted = ActivityCompat.checkSelfPermission(
            context,
            CAMERA
        ) != PackageManager.PERMISSION_GRANTED

        if (cameraPermissionNotGranted) {
            requestCameraPermissionLauncher.launch(CAMERA)
        } else {
            takeImageCameraLauncher.launch(imageUri)
        }
    }

    private fun openGallery() {
        val context = requireContext()

        val galleryPermissionNotGranted = ActivityCompat.checkSelfPermission(
            context,
            READ_EXTERNAL_STORAGE
        ) != PackageManager.PERMISSION_GRANTED

        if (galleryPermissionNotGranted) {
            requestCameraPermissionLauncher.launch(READ_EXTERNAL_STORAGE)
        } else {
            takeImageCameraLauncher.launch(imageUri)
        }
    }
}