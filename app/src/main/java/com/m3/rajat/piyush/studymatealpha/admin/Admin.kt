package com.m3.rajat.piyush.studymatealpha.admin
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.m3.rajat.piyush.studymatealpha.MainActivity
import com.m3.rajat.piyush.studymatealpha.database.AdminViewModel
import com.m3.rajat.piyush.studymatealpha.database.PasswordUtils
import com.m3.rajat.piyush.studymatealpha.databinding.ActivityAdminBinding

class Admin : AppCompatActivity() {
    private lateinit var binding : ActivityAdminBinding
    private lateinit var adminSession: AdminSession
    private lateinit var viewModel: AdminViewModel

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[AdminViewModel::class.java]
        adminSession = AdminSession(this)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        viewModel.loginResult.observe(this) { admin ->
            if (admin != null) {
                val pass = binding.EdtAdminPasswd.text.toString()
                if (PasswordUtils.verifyPassword(pass, admin.adminPassword)) {
                    Toast.makeText(applicationContext, "SignIn Successfully", Toast.LENGTH_SHORT).show()
                    adminSession.adminLogin(admin.adminEmail, admin.adminName, admin.adminId!!)
                    startActivity(Intent(this, Admin_panel::class.java))
                } else {
                    Toast.makeText(applicationContext, "Wrong Password", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(applicationContext, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                Toast.makeText(applicationContext, "Please Try To Create A Admin First !!", Toast.LENGTH_LONG).show()
                clearAdmin()
            }
        }

        binding.btnLogin.setOnClickListener {
            validateUser()
        }
        binding.btnSignUp.setOnClickListener {
            startActivity(Intent(this,AdminCreate::class.java))
        }
        if(adminSession.login()){
            startActivity(Intent(applicationContext, Admin_panel::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
            finish()
        }

        binding.topAppBar.setNavigationOnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }
    }

    private fun validateUser(): Boolean {
        if (binding.EdtAdminEmail.length() == 0) {
            binding.EdtAdminEmail.error = "Email ID Required"
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.EdtAdminEmail.text.toString()).matches()) {
            Toast.makeText(this, "Email Format Wrong!", Toast.LENGTH_SHORT).show()
            return false
        } else if (binding.EdtAdminPasswd.length() == 0) {
            binding.EdtAdminPasswd.error = "Password Required"
            return false
        } else {
            val email = binding.EdtAdminEmail.text.toString()
            viewModel.checkAdmin(email)
            return true
        }
    }

    private fun clearAdmin() {
        binding.EdtAdminEmail.setText("")
        binding.EdtAdminPasswd.setText("")
    }
}