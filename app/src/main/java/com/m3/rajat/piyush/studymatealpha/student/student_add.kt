package com.m3.rajat.piyush.studymatealpha.student
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.m3.rajat.piyush.studymatealpha.R
import com.m3.rajat.piyush.studymatealpha.admin.Admin_panel
import com.m3.rajat.piyush.studymatealpha.database.StudentEntity
import com.m3.rajat.piyush.studymatealpha.database.StudentViewModel
import com.m3.rajat.piyush.studymatealpha.database.PasswordUtils
import com.m3.rajat.piyush.studymatealpha.databinding.ActivityStudentAddBinding
import java.io.ByteArrayOutputStream
import java.util.Random

class student_add : AppCompatActivity() {
    private lateinit var binding : ActivityStudentAddBinding
    private lateinit var viewModel: StudentViewModel
    private lateinit var student_name : EditText
    private lateinit var student_email : EditText
    private lateinit var student_password : EditText
    private lateinit var student_class : EditText
    private var byteArray: ByteArray? = null
    private var STUD_ID = Random().nextInt(2300000 - 2200000) + 2200000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[StudentViewModel::class.java]

        student_name = findViewById(R.id.Admin_add_Student_name)
        student_email = findViewById(R.id.Admin_add_student_email)
        student_password = findViewById(R.id.Admin_add_student_pass)
        student_class = findViewById(R.id.Admin_add_student_class)

        binding.btnAdminAddStudent.setOnClickListener { addStudent() }

        binding.studentImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
            imagePickerLauncher.launch(intent)
        }

        viewModel.operationResult.observe(this) { success ->
            if (success) {
                Toast.makeText(this,"Student Added",Toast.LENGTH_SHORT).show()
                startActivity(Intent(applicationContext, Admin_panel::class.java))
            } else {
                Toast.makeText(this,"Registration Failed",Toast.LENGTH_SHORT).show()
            }
        }

        binding.topAppBar.setNavigationOnClickListener {
            startActivity(Intent(applicationContext, Admin_panel::class.java))
            finish()
        }
    }

    private fun addStudent() {
        val name = student_name.text.toString()
        val email = student_email.text.toString()
        val pass = PasswordUtils.hashPassword(student_password.text.toString())
        val sub = student_class.text.toString().uppercase()

        val student = StudentEntity(
            studentId = STUD_ID,
            studentImage = byteArray,
            studentName = name,
            studentEmail = email,
            studentPassword = pass,
            studentClass = sub
        )
        viewModel.insertStudent(student)
    }

    private val imagePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val uri = result.data?.data ?: return@registerForActivityResult
            var inputStream: java.io.InputStream? = null
            try {
                inputStream = contentResolver.openInputStream(uri)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream)
                val imgBytes = stream.toByteArray()
                if (imgBytes.size / 1024 < 2048) {
                    binding.studentImage.setImageBitmap(bitmap)
                    byteArray = imgBytes
                } else {
                    Toast.makeText(this, "Please choose an image below 2mb", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            } finally {
                inputStream?.close()
            }
        }
    }
}