package com.m3.rajat.piyush.studymatealpha.faculty
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.m3.rajat.piyush.studymatealpha.R
import com.m3.rajat.piyush.studymatealpha.database.FacultyViewModel
import com.m3.rajat.piyush.studymatealpha.databinding.ActivityFacultyPanelBinding

@Suppress("DEPRECATION")
class Faculty_panel : AppCompatActivity() {
    private lateinit var binding: ActivityFacultyPanelBinding
    private lateinit var viewModel: FacultyViewModel
    private lateinit var facultySession: FacultySession
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFacultyPanelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[FacultyViewModel::class.java]
        facultySession = FacultySession(this)

        val facultyId = facultySession.sharedPreferences.getInt("id", 0)
        Log.d("fid",facultyId.toString())

        viewModel.getById(facultyId) { faculty ->
            runOnUiThread {
                if (faculty != null) {
                    binding.facultyId.setText(faculty.facultyId.toString())
                    binding.facultyName.setText(faculty.facultyName)
                    binding.facultyEmail.setText(faculty.facultyEmail)
                    binding.facultySub.setText(faculty.facultySub)
                    if (faculty.facultyImage != null) {
                        binding.facultyImage.setImageBitmap(
                            BitmapFactory.decodeByteArray(faculty.facultyImage, 0, faculty.facultyImage.size)
                        )
                    } else {
                        binding.facultyImage.setImageDrawable(resources.getDrawable(R.drawable.add_img))
                    }
                }
            }
        }

        binding.btnLogout.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setMessage("Are you sure want to logout ?")
                .setTitle("Information")
                .setCancelable(true)
                .setPositiveButton("Yes"){ dialog, _ ->
                    facultySession.facultyLogout()
                    startActivity(Intent(applicationContext, Faculty::class.java))
                    finish()
                    dialog.dismiss()
                }
                .setNegativeButton("No"){ dialog, _ -> dialog.dismiss() }
                .create().show()
        }

        binding.topAppBar.setNavigationOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setMessage("Are you sure want to logout ?")
                .setTitle("Information")
                .setCancelable(true)
                .setPositiveButton("Yes"){ dialog, _ ->
                    facultySession.facultyLogout()
                    startActivity(Intent(applicationContext, Faculty::class.java))
                    finish()
                    dialog.dismiss()
                }
                .setNegativeButton("No"){ dialog, _ -> dialog.dismiss() }
                .create().show()
        }
    }
}