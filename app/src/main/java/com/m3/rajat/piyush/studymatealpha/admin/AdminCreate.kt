package com.m3.rajat.piyush.studymatealpha.admin

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.m3.rajat.piyush.studymatealpha.database.AdminEntity
import com.m3.rajat.piyush.studymatealpha.database.AdminViewModel
import com.m3.rajat.piyush.studymatealpha.database.PasswordUtils
import com.m3.rajat.piyush.studymatealpha.R
import com.m3.rajat.piyush.studymatealpha.databinding.ActivityAdminCreateBinding
import java.util.Random

class AdminCreate : AppCompatActivity() {

    private lateinit var binding: ActivityAdminCreateBinding
    private lateinit var viewModel: AdminViewModel
    private var adminId = Random().nextInt(2200000 - 2100000) + 2100000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this)[AdminViewModel::class.java]

        viewModel.operationResult.observe(this) { success ->
            if (success) {
                Toast.makeText(applicationContext, "SignUp Successfully", Toast.LENGTH_SHORT).show()
                clearAdmin()
                finish()
            } else {
                Toast.makeText(applicationContext, "Account Already Exists", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnCreateAccount.setOnClickListener {
            if (validateUser()) addAdmin()
        }

        binding.btnBack.setOnClickListener {
            startActivity(Intent(this, Admin::class.java))
        }
    }

    private fun validateUser(): Boolean {
        if (binding.EdtAdminName.length() == 0) {
            binding.EdtAdminName.error = "Name Required"
            return false
        } else if (binding.EdtAdminEmail.length() == 0) {
            binding.EdtAdminEmail.error = "Email ID Required"
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.EdtAdminEmail.text.toString()).matches()) {
            Toast.makeText(this, "Email Format Wrong!", Toast.LENGTH_SHORT).show()
            return false
        } else if (binding.EdtAdminPasswd.length() == 0) {
            binding.EdtAdminPasswd.error = "Password Required"
            return false
        }
        return true
    }

    private fun addAdmin() {
        val name = binding.EdtAdminName.text.toString()
        val email = binding.EdtAdminEmail.text.toString()
        val pass = PasswordUtils.hashPassword(binding.EdtAdminPasswd.text.toString())

        val admin = AdminEntity(
            adminId = adminId,
            adminName = name,
            adminEmail = email,
            adminPassword = pass,
        )

        viewModel.insertAdmin(admin)
    }

    private fun clearAdmin() {
        binding.EdtAdminName.setText("")
        binding.EdtAdminEmail.setText("")
        binding.EdtAdminPasswd.setText("")
    }
}
