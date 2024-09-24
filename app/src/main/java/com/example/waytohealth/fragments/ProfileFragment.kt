package com.example.waytohealth.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.waytohealth.DBHelper
import com.example.waytohealth.R
import com.google.android.material.textfield.TextInputEditText
import java.io.File
import java.io.FileOutputStream

class ProfileFragment : Fragment() {
    private lateinit var profileImageView: ImageView
    private val PICK_IMAGE = 1
    private val warning: String = "Пожалуйста, заполните все поля"
    private val saved: String = "Данные сохранены"
    private val zeroGoal: String = "Цель не может быть нулём"


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadProfileData()
    }

    override fun onResume() {
        super.onResume()
        val saveButton = requireView().findViewById<Button>(R.id.save_profile_button)
        val db = DBHelper(requireContext(), null)
        saveButton.setOnClickListener {
            saveProfileData(db)
        }
        changeIfTypesAchieved()
        changeIfCountAchieved(db)
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

    private fun changeIfTypesAchieved() {
        val sharedPrefs = requireContext().getSharedPreferences("TypePrefs", Context.MODE_PRIVATE)

        val balancingAchievement =
            sharedPrefs.getString("balancing", false.toString()) == true.toString()
        val flexibilityAchievement =
            sharedPrefs.getString("flexibility", false.toString()) == true.toString()
        val powerAchievement = sharedPrefs.getString("power", false.toString()) == true.toString()

        val achiveFlexibility = requireView().findViewById<ImageView>(R.id.ivIcon7)
        val achivePower = requireView().findViewById<ImageView>(R.id.ivIcon8)
        val achiveBalance = requireView().findViewById<ImageView>(R.id.ivIcon9)

        if (flexibilityAchievement) {
            achiveFlexibility.setImageResource(R.drawable.flexibility_c)
        }
        if (powerAchievement) {
            achivePower.setImageResource(R.drawable.power_c)
        }
        if (balancingAchievement) {
            achiveBalance.setImageResource(R.drawable.balance_c)
        }
    }

    private fun loadProfileData() {
        val db = DBHelper(requireContext(), null)
        val profileData = db.getProfileData()
        if (profileData != null) {
            val (nameDb, ageDb, goalDb) = profileData
            requireView().findViewById<TextInputEditText>(R.id.nameEditText).setText(nameDb)
            requireView().findViewById<TextInputEditText>(R.id.ageEditText)
                .setText(ageDb.toString())
            requireView().findViewById<TextInputEditText>(R.id.goalEditText)
                .setText(goalDb.toString())
        }
    }

    private fun changeIfCountAchieved(db: DBHelper) {
        val count: Int = db.getTrainingCount()
        val achieve1 = requireView().findViewById<ImageView>(R.id.ivIcon1)
        val achieve5 = requireView().findViewById<ImageView>(R.id.ivIcon2)
        val achieve15 = requireView().findViewById<ImageView>(R.id.ivIcon3)
        val achieve30 = requireView().findViewById<ImageView>(R.id.ivIcon4)
        val achieve50 = requireView().findViewById<ImageView>(R.id.ivIcon5)
        val achieve100 = requireView().findViewById<ImageView>(R.id.ivIcon6)

        if (count >= 1) {
            achieve1.setImageResource(R.drawable.one_day_c)
        }
        if (count >= 5) {
            achieve5.setImageResource(R.drawable.five_days_c)
        }
        if (count >= 15) {
            achieve15.setImageResource(R.drawable.fifteen_days_c)

        }
        if (count >= 30) {
            achieve30.setImageResource(R.drawable.thirty_days_c)
        }
        if (count >= 50) {
            achieve50.setImageResource(R.drawable.fifty_days_c)
        }
        if (count >= 100) {
            achieve100.setImageResource(R.drawable.hundred_days_c)

        }
    }

    private fun saveProfileData(db: DBHelper) {
        val name =
            requireView().findViewById<TextInputEditText>(R.id.nameEditText).text.toString().trim()
        val age =
            requireView().findViewById<TextInputEditText>(R.id.ageEditText).text.toString().trim()
        val goal =
            requireView().findViewById<TextInputEditText>(R.id.goalEditText).text.toString().trim()
        if (name.isNotEmpty() && age.isNotEmpty() && goal.isNotEmpty()) {
            if (goal == "0") {
                Toast.makeText(requireContext(), zeroGoal, Toast.LENGTH_SHORT).show()
            } else {
                if (db.getProfileData() == null) {
                    db.addProfileData(name, age.toInt(), goal.toInt())
                } else {
                    db.updateProfileData(name, age.toInt(), goal.toInt())
                }
                Toast.makeText(requireContext(), saved, Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireContext(), warning, Toast.LENGTH_SHORT).show()
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
