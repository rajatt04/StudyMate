package com.m3.rajat.piyush.studymatealpha.faculty
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
import com.m3.rajat.piyush.studymatealpha.database.FacultyEntity
import com.m3.rajat.piyush.studymatealpha.database.FacultyViewModel
import com.m3.rajat.piyush.studymatealpha.database.PasswordUtils
import com.m3.rajat.piyush.studymatealpha.databinding.ActivityFacultyAddBinding
import java.io.ByteArrayOutputStream
import java.util.Random

class faculty_add : AppCompatActivity() {
    private lateinit var binding : ActivityFacultyAddBinding
    private lateinit var viewModel: FacultyViewModel
    private lateinit var faculty_name : EditText
    private lateinit var faculty_email : EditText
    private lateinit var faculty_password : EditText
    private lateinit var faculty_sub : EditText
    private var byteArray: ByteArray? = null
    private var FAC_ID = Random().nextInt(2200000 - 2100000) + 2100000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFacultyAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[FacultyViewModel::class.java]

        faculty_name = findViewById(R.id.Admin_add_faculty_name)
        faculty_email = findViewById(R.id.Admin_add_faculty_email)
        faculty_password = findViewById(R.id.Admin_add_faculty_pass)
        faculty_sub = findViewById(R.id.Admin_add_faculty_sub)

        binding.btnAdminAddFaculty.setOnClickListener { addFaculty() }

        binding.facultyImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
            imagePickerLauncher.launch(intent)
        }

        viewModel.operationResult.observe(this) { success ->
            if (success) {
                Toast.makeText(this,"Faculty Added",Toast.LENGTH_SHORT).show()
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

    private fun addFaculty() {
        val name = faculty_name.text.toString()
        val email = faculty_email.text.toString()
        val pass = PasswordUtils.hashPassword(faculty_password.text.toString())
        val sub = faculty_sub.text.toString()

        val faculty = FacultyEntity(
            facultyId = FAC_ID,
            facultyImage = byteArray,
            facultyName = name,
            facultyEmail = email,
            facultyPassword = pass,
            facultySub = sub
        )
        viewModel.insertFaculty(faculty)
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
                    binding.facultyImage.setImageBitmap(bitmap)
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