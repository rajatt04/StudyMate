package com.m3.rajat.piyush.studymatealpha.assignment
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.m3.rajat.piyush.studymatealpha.database.AssignmentEntity
import com.m3.rajat.piyush.studymatealpha.database.AssignmentViewModel
import com.m3.rajat.piyush.studymatealpha.R
import com.m3.rajat.piyush.studymatealpha.admin.Admin_panel
import com.m3.rajat.piyush.studymatealpha.databinding.ActivityAssignmentAddBinding

class assignment_add : AppCompatActivity() {
    private lateinit var binding : ActivityAssignmentAddBinding
    private lateinit var viewModel: AssignmentViewModel
    private lateinit var assignment_name: TextInputEditText
    private lateinit var assignment_sdate: TextInputEditText
    private lateinit var assignment_type: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAssignmentAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[AssignmentViewModel::class.java]

        assignment_name = findViewById(R.id.Admin_add_assignment_name)
        assignment_sdate = findViewById(R.id.Admin_add_assignment_sdate)
        assignment_type = findViewById(R.id.Admin_add_assignment_type)

        assignment_sdate.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .build()
            datePicker.show(supportFragmentManager, "DATE_PICKER")
            datePicker.addOnPositiveButtonClickListener {
                assignment_sdate.setText(datePicker.headerText)
            }
        }

        viewModel.operationResult.observe(this) { success ->
            if (success) {
                Toast.makeText(this,"Assignment Added",Toast.LENGTH_SHORT).show()
                startActivity(Intent(applicationContext, Admin_panel::class.java))
            }
        }

        binding.btnAdminAddAssignment.setOnClickListener {
            if (validation()) {
                val name = assignment_name.text.toString()
                val sdate = assignment_sdate.text.toString()
                val stype = assignment_type.text.toString()
                val assignment = AssignmentEntity(assignmentName = name, assignmentSdate = sdate, assignmentType = stype)
                viewModel.insertAssignment(assignment)
            }
        }

        binding.topAppBar.setNavigationOnClickListener {
            startActivity(Intent(applicationContext, Admin_panel::class.java))
            finish()
        }
    }

    private fun validation(): Boolean {
        if(assignment_name.length()==0){
            assignment_name.error = "Assignment Name Required"
            return false
        }else if(assignment_sdate.length()==0){
            assignment_sdate.error = "Date Required"
            return false
        }else if(assignment_type.length()==0){
            assignment_type.error = "Type Required"
            return false
        }
        return true
    }
}