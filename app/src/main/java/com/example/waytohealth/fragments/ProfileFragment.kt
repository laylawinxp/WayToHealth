package com.example.waytohealth.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.waytohealth.R
import java.io.File
import java.io.FileOutputStream

class ProfileFragment : Fragment() {
    private lateinit var profileImageView: ImageView
    private val PICK_IMAGE = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        profileImageView = view.findViewById(R.id.profile_image_view)

        loadProfileImage()

        profileImageView.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE)
        }
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri: Uri? = data.data
            profileImageView.setImageURI(selectedImageUri)
            selectedImageUri?.let { uri ->
                saveImageToAppFiles(requireContext(), uri)
            }
        }
    }

    private fun saveImageToAppFiles(context: Context, uri: Uri) {
        val inputStream = context.contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(File(context.getExternalFilesDir(null), "myImage.jpg"))
        inputStream.use { input ->
            outputStream.use { output ->
                input?.copyTo(output)
            }
        }
    }

    private fun loadProfileImage() {
        val file = File(requireContext().getExternalFilesDir(null), "myImage.jpg")
        if (file.exists()) {
            val bitmap = BitmapFactory.decodeFile(file.absolutePath)
            profileImageView.setImageBitmap(bitmap)
        }
    }
}
