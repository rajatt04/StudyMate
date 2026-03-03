package com.m3.rajat.piyush.studymatealpha.student

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.m3.rajat.piyush.studymatealpha.R
import com.m3.rajat.piyush.studymatealpha.database.FacultyViewModel
import com.m3.rajat.piyush.studymatealpha.database.PasswordUtils
import com.m3.rajat.piyush.studymatealpha.database.StudentViewModel
import com.m3.rajat.piyush.studymatealpha.faculty.Faculty
import com.m3.rajat.piyush.studymatealpha.faculty.FacultySession
import com.m3.rajat.piyush.studymatealpha.faculty.Faculty_panel

class Student : AppCompatActivity() {

    private lateinit var userId : EditText
    private lateinit var userPasswd : EditText
    private lateinit var userLogin : Button
    private lateinit var userBack : Button
    private lateinit var facultyViewModel: FacultyViewModel
    private lateinit var studentViewModel: StudentViewModel
    private lateinit var facultySession: FacultySession
    private lateinit var studentSession: StudentSession

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)

        userId = findViewById(R.id.EdtUserId)
        userPasswd = findViewById(R.id.EdtUserPassword)
        userLogin = findViewById(R.id.userLogin)
        userBack = findViewById(R.id.userBack)

        facultyViewModel = ViewModelProvider(this)[FacultyViewModel::class.java]
        studentViewModel = ViewModelProvider(this)[StudentViewModel::class.java]
        facultySession = FacultySession(this)
        studentSession = StudentSession(this)

        val student = intent.getStringExtra("student_email")
        val faculty = intent.getStringExtra("faculty_email")

        if (student != null) {
            studentViewModel.getById(0) {} // placeholder
            // Use the lookup to populate ID
            studentViewModel.isStudent(student)
            studentViewModel.lookupResult.observe(this) { list ->
                if (list.isNotEmpty()) {
                    userId.setText(list[0].studentId.toString())
                }
            }
        }

        if (faculty != null) {
            facultyViewModel.isFaculty(faculty)
            facultyViewModel.lookupResult.observe(this) { list ->
                if (list.isNotEmpty()) {
                    userId.setText(list[0].facultyId.toString())
                }
            }
        }

        userLogin.setOnClickListener {
            val id = userId.text.toString()
            if (validation_student() && id.isNotEmpty()) {
                val idInt = id.toInt()
                facultyViewModel.getById(idInt) { fac ->
                    runOnUiThread {
                        if (fac != null) {
                            if (PasswordUtils.verifyPassword(userPasswd.text.toString(), fac.facultyPassword)) {
                                facultySession.facultyLogin(idInt)
                                showToast("Login Successfully !")
                                startActivity(Intent(applicationContext, Faculty_panel::class.java))
                                userId.text.clear()
                                userPasswd.text.clear()
                            } else {
                                showToast("Incorrect Password !")
                            }
                        } else {
                            studentViewModel.getById(idInt) { std ->
                                runOnUiThread {
                                    if (std != null) {
                                        if (PasswordUtils.verifyPassword(userPasswd.text.toString(), std.studentPassword)) {
                                            studentSession.studentLogin(idInt)
                                            showToast("Login Successfully !")
                                            startActivity(Intent(applicationContext, Student_panel::class.java))
                                            userId.text.clear()
                                            userPasswd.text.clear()
                                        } else {
                                            showToast("Incorrect Password !")
                                        }
                                    } else {
                                        showToast("User not found !")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        userBack.setOnClickListener {
            startActivity(Intent(applicationContext, Faculty::class.java))
        }
    }

    private fun validation_student(): Boolean {
        if(userPasswd.length()==0){
            userPasswd.error = "Password cann't be empty"
            return false
        }
        return true
    }

    private fun showToast(str : String){
        Toast.makeText(applicationContext,str,Toast.LENGTH_SHORT).show()
    }
}