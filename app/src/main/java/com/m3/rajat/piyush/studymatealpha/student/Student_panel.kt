package com.m3.rajat.piyush.studymatealpha.student
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.m3.rajat.piyush.studymatealpha.faculty.Faculty
import com.m3.rajat.piyush.studymatealpha.R
import com.m3.rajat.piyush.studymatealpha.database.StudentViewModel
import com.m3.rajat.piyush.studymatealpha.databinding.ActivityStudentPanelBinding

@Suppress("DEPRECATION")
class Student_panel : AppCompatActivity() {
    private lateinit var binding: ActivityStudentPanelBinding
    private lateinit var viewModel: StudentViewModel
    private lateinit var studentSession: StudentSession
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentPanelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[StudentViewModel::class.java]
        studentSession = StudentSession(this)

        val studentId = studentSession.sharedPreferences.getInt("id", 0)
        Log.d("sid",studentId.toString())

        viewModel.getById(studentId) { student ->
            runOnUiThread {
                if (student != null) {
                    binding.studentId.setText(student.studentId.toString())
                    binding.studentName.setText(student.studentName)
                    binding.studentEmail.setText(student.studentEmail)
                    binding.studentClass.setText(student.studentClass)
                    if (student.studentImage != null) {
                        binding.studentImage.setImageBitmap(
                            BitmapFactory.decodeByteArray(student.studentImage, 0, student.studentImage.size)
                        )
                    } else {
                        binding.studentImage.setImageDrawable(resources.getDrawable(R.drawable.add_img))
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
                    studentSession.studentLogout()
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
                    studentSession.studentLogout()
                    startActivity(Intent(applicationContext, Faculty::class.java))
                    finish()
                    dialog.dismiss()
                }
                .setNegativeButton("No"){ dialog, _ -> dialog.dismiss() }
                .create().show()
        }
    }
}