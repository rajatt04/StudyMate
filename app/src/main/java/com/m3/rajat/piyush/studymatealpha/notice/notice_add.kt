package com.m3.rajat.piyush.studymatealpha.notice
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.m3.rajat.piyush.studymatealpha.R
import com.m3.rajat.piyush.studymatealpha.admin.Admin_panel
import com.m3.rajat.piyush.studymatealpha.database.NoticeEntity
import com.m3.rajat.piyush.studymatealpha.database.NoticeViewModel
import com.m3.rajat.piyush.studymatealpha.databinding.ActivityNoticeAddBinding

class notice_add : AppCompatActivity() {
    private lateinit var binding : ActivityNoticeAddBinding
    private lateinit var viewModel: NoticeViewModel
    private lateinit var notice_name: TextInputEditText
    private lateinit var notice_des: TextInputEditText
    private lateinit var notice_date: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoticeAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[NoticeViewModel::class.java]

        notice_name = findViewById(R.id.Admin_add_notice_name)
        notice_des = findViewById(R.id.Admin_add_notice_des)
        notice_date = findViewById(R.id.Admin_add_notice_date)

        notice_date.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .build()
            datePicker.show(supportFragmentManager, "DATE_PICKER")
            datePicker.addOnPositiveButtonClickListener {
                notice_date.setText(datePicker.headerText)
            }
        }

        viewModel.operationResult.observe(this) { success ->
            if (success) {
                Toast.makeText(this,"Notice Added",Toast.LENGTH_SHORT).show()
                startActivity(Intent(applicationContext, Admin_panel::class.java))
            }
        }

        binding.btnAdminAddNotice.setOnClickListener {
            if (validation()) {
                val name = notice_name.text.toString()
                val des = notice_des.text.toString()
                val ndate = notice_date.text.toString()
                val notice = NoticeEntity(noticeName = name, noticeDes = des, noticeDate = ndate)
                viewModel.insertNotice(notice)
            }
        }

        binding.topAppBar.setNavigationOnClickListener {
            startActivity(Intent(applicationContext, Admin_panel::class.java))
            finish()
        }
    }

    private fun validation(): Boolean {
        if(notice_name.length()==0){
            notice_name.error = "Notice Name Required"
            return false
        }else if(notice_des.length()==0){
            notice_des.error = "Notice Description Required"
            return false
        }else if(notice_date.length()==0){
            notice_date.error = "Date Required"
            return false
        }
        return true
    }
}