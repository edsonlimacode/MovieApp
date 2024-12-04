package com.edsonlima.flixapp.presenter.main.profile

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.service.autofill.Sanitizer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.edsonlima.flixapp.R
import com.edsonlima.flixapp.databinding.BottomSheetProfileBinding
import com.edsonlima.flixapp.databinding.BottomSheetRequestPermissionBinding
import com.edsonlima.flixapp.databinding.FragmentEditProfileBinding
import com.edsonlima.flixapp.domain.model.User
import com.edsonlima.flixapp.utils.FirebaseHelper
import com.edsonlima.flixapp.utils.StateView
import com.edsonlima.flixapp.utils.initToolBar
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class EditProfileFragment : Fragment() {

    private lateinit var binding: FragmentEditProfileBinding

    private val profileViewModel: ProfileViewModel by viewModels()

    private val GALLERY_PERMISSION = Manifest.permission.READ_EXTERNAL_STORAGE
    private val CAMERA_PERMISSION = Manifest.permission.CAMERA

    private var currentPhotoUri: Uri? = null

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            currentPhotoUri = it
            binding.imgProfile.setImageURI(it)
        }
    }

    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let {
                currentPhotoUri = it
                binding.imgProfile.setImageURI(it)
            }
        }

    private val takePictureLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success: Boolean ->
        if (success) {
            binding.imgProfile.setImageURI(currentPhotoUri)
        }
    }

    private val requestGalleryPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                pickImageLauncher.launch("image/*")
            } else {
                showBottomSheetPermissionDenied()
            }
        }
    private val requestCameraPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                openCamera()
            } else {
                showBottomSheetPermissionDenied()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentEditProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolBar(binding.tbProfile)
        initSpinner()
        initObservers()
        initListeners()
        getUserById()
    }

    private fun initSpinner() {
        binding.spGenre.adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.genre,
            R.layout.item_profile_genre_select
        )
    }

    private fun getUserById() {
        profileViewModel.getUserById(FirebaseHelper.getUserId())
    }

    private fun initListeners() {

        binding.btnSelectPhoto.setOnClickListener {

            bottomSheetMediaProfile()
        }

        binding.btnUpdateProfile.setOnClickListener {
            profileViewModel.validate(
                binding.editNameProfile.text.toString(),
                binding.editLastNameProfile.text.toString(),
                binding.spGenre.selectedItem.toString(),
                binding.spGenre.selectedItemPosition
            )
        }
    }

    private fun initObservers() {

        profileViewModel.userState.observe(viewLifecycleOwner) { stateView ->

            when (stateView) {

                is StateView.Loading -> {
                    binding.pbProfile.isVisible = true
                }

                is StateView.Success -> {

                    binding.pbProfile.isVisible = false

                    stateView.data?.let {
                        binding.editNameProfile.setText(it.name)
                        binding.editLastNameProfile.setText(it.lastName)

                        val options = resources.getStringArray(R.array.genre)
                        val position = options.indexOf(it.genre)

                        if (position != -1) {
                            binding.spGenre.setSelection(position)
                        }
                    }

                    binding.editEmailProfile.setText(FirebaseHelper.getAuth().currentUser?.email)

                }

                is StateView.Error -> {
                    binding.pbProfile.isVisible = false
                    Toast.makeText(requireContext(), stateView.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        profileViewModel.validate.observe(viewLifecycleOwner) { (validated, message) ->
            if (validated) {

                update()

                Snackbar.make(binding.root, "Perfil atualizado com sucesso", Snackbar.LENGTH_LONG)
                    .show()
            } else {
                Snackbar.make(binding.root, message.toString(), Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun update() {
        val user = User(
            name = binding.editNameProfile.text.toString(),
            genre = binding.spGenre.selectedItem.toString(),
            lastName = binding.editLastNameProfile.text.toString(),
        )

        profileViewModel.update(user)
    }

    private fun bottomSheetMediaProfile() {
        val bottomSheet = BottomSheetDialog(requireContext())

        val bottomSheetProfileBinding =
            BottomSheetProfileBinding.inflate(layoutInflater, null, false)

        bottomSheet.setContentView(bottomSheetProfileBinding.root)

        bottomSheetProfileBinding.btnCamera.setOnClickListener {
            bottomSheet.dismiss()
            checkCameraPermission()
        }

        bottomSheetProfileBinding.btnGallery.setOnClickListener {
            bottomSheet.dismiss()
            checkGalleryPermission()
        }

        bottomSheet.show()
    }

    private fun showBottomSheetPermissionDenied() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val bottomSheetBinding = BottomSheetRequestPermissionBinding.inflate(
            layoutInflater, null, false
        )

        bottomSheetDialog.setContentView(bottomSheetBinding.root)

        bottomSheetBinding.btnCancel.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetBinding.btnAccept.setOnClickListener {
            bottomSheetDialog.dismiss()

            val intent = Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", requireActivity().packageName, null)
            )
            startActivity(intent)
        }

        bottomSheetDialog.show()
    }

    private fun checkGalleryPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            if (checkPermissionGranted(GALLERY_PERMISSION)) {
                pickImageLauncher.launch("image/*")
            } else {
                requestGalleryPermission.launch(GALLERY_PERMISSION)
            }
        } else {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }

    private fun checkCameraPermission() {
        if (checkPermissionGranted(CAMERA_PERMISSION)) {
            openCamera()
        } else {
            requestCameraPermission.launch(CAMERA_PERMISSION)
        }
    }

    private fun openCamera() {
        val photoFile = createImageFile()
        photoFile?.let {
            currentPhotoUri = FileProvider.getUriForFile(
                requireContext(),
                "${requireContext().packageName}.provider",
                it
            )
            takePictureLauncher.launch(currentPhotoUri)
        }
    }

    private fun checkPermissionGranted(permission: String) =
        ContextCompat.checkSelfPermission(
            requireContext(),
            permission
        ) == PackageManager.PERMISSION_GRANTED

    private fun createImageFile(): File? {
        val timeStamp: String =
            SimpleDateFormat("yyyy-MM-dd_HH:mm:ss", Locale.getDefault()).format(Date())
        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imageFile = File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        )
        return imageFile
    }
}