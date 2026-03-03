package com.m3.rajat.piyush.studymatealpha.student
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.m3.rajat.piyush.studymatealpha.R
import com.m3.rajat.piyush.studymatealpha.database.StudentEntity
import com.m3.rajat.piyush.studymatealpha.database.StudentViewModel
import com.m3.rajat.piyush.studymatealpha.databinding.ActivityStudentUpdateBinding

class student_update : AppCompatActivity() {
    private lateinit var upd_name : EditText
    private lateinit var upd_email : EditText
    private lateinit var upd_password : EditText
    private lateinit var upd_class : EditText
    private lateinit var upd_image : ImageView
    private lateinit var upd_id : EditText
    private lateinit var btn_upd : Button
    private lateinit var btn_delete : Button
    private lateinit var binding : ActivityStudentUpdateBinding
    private lateinit var viewModel: StudentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        viewModel = ViewModelProvider(this)[StudentViewModel::class.java]

        upd_name.setText(intent.getStringExtra("student_name"))
        upd_email.setText(intent.getStringExtra("student_email"))
        upd_password.setText(intent.getStringExtra("student_pass"))
        upd_class.setText(intent.getStringExtra("student_class"))
        upd_id.setText(intent.getIntExtra("student_id",0).toString())
        if(intent.getByteArrayExtra("student_image")!=null){
            upd_image.setImageBitmap(BitmapFactory.decodeByteArray(intent.getByteArrayExtra("student_image"),0,intent.getByteArrayExtra("student_image")!!.size))
        }

        viewModel.operationResult.observe(this) { success ->
            if (success) {
                Toast.makeText(applicationContext,"Update",Toast.LENGTH_SHORT).show()
                startActivity(Intent(applicationContext, student_view::class.java))
            } else {
                Toast.makeText(applicationContext,"Error",Toast.LENGTH_SHORT).show()
            }
        }

        btn_upd.setOnClickListener {
            if(validation()) updateStudent()
            else Toast.makeText(this,"Something Went Wrong",Toast.LENGTH_SHORT).show()
        }

        btn_delete.setOnClickListener {
            val email = intent.getStringExtra("student_email")
            if(email!=null) deleteStudent(email)
        }

        binding.topAppBar.setNavigationOnClickListener {
            startActivity(Intent(applicationContext, student_view::class.java))
            finish()
        }
    }

    private fun validation(): Boolean {
        if(upd_name.length() == 0){ upd_name.error = "Name Required"; return false }
        else if(upd_password.length()==0){ upd_password.error = "Password Not Be Null"; return false }
        else if(upd_class.length()==0){ upd_class.error = "Class Can't Be Empty"; return false }
        return true
    }

    private fun updateStudent() {
        val student = StudentEntity(
            studentId = upd_id.text.toString().toInt(),
            studentName = upd_name.text.toString(),
            studentEmail = upd_email.text.toString(),
            studentClass = upd_class.text.toString(),
            studentPassword = upd_password.text.toString()
        )
        viewModel.updateStudent(student)
    }

    private fun deleteStudent(email: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Student")
            .setMessage("Do You Want To Delete This Student ?")
            .setCancelable(true)
            .setPositiveButton("Yes") { dialog,_ ->
                viewModel.deleteStudent(email)
                dialog.dismiss()
                startActivity(Intent(applicationContext, student_view::class.java))
            }
            .setNegativeButton("No"){ dialog,_ -> dialog.dismiss() }
            .create().show()
    }

    private fun initView() {
        upd_name = findViewById(R.id.Admin_update_student_name)
        upd_email = findViewById(R.id.Admin_update_student_email)
        upd_password = findViewById(R.id.Admin_update_student_pass)
        upd_class = findViewById(R.id.Admin_update_student_class)
        upd_id = findViewById(R.id.Admin_update_student_Id)
        upd_image = findViewById(R.id.Admin_update_student_image)
        btn_upd = findViewById(R.id.btnAdmin_update_student)
        btn_delete = findViewById(R.id.btnDeleteStudent)
    }
}