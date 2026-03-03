package com.m3.rajat.piyush.studymatealpha.admin
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.m3.rajat.piyush.studymatealpha.R
import com.m3.rajat.piyush.studymatealpha.database.AdminViewModel
import com.m3.rajat.piyush.studymatealpha.databinding.ActivityAdminViewBinding

@Suppress("DEPRECATION")
class Admin_view : AppCompatActivity() {

    private lateinit var viewModel: AdminViewModel
    private lateinit var adminSession: AdminSession
    private lateinit var id : EditText
    private lateinit var name: EditText
    private lateinit var email : EditText
    private lateinit var image : ImageView
    private lateinit var btn_update : Button
    private  lateinit var  binding : ActivityAdminViewBinding
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[AdminViewModel::class.java]
        adminSession = AdminSession(this)

        id = findViewById(R.id.Admin_updateId)
        name = findViewById(R.id.Admin_updatename)
        email = findViewById(R.id.Admin_updateemail)
        image = findViewById(R.id.Admin_updateimage)
        btn_update = findViewById(R.id.btnAdmin_update)

        val adminId = adminSession.sharedPreferences.getInt("id",0)

        viewModel.getAdminById(adminId) { admin ->
            runOnUiThread {
                if (admin != null) {
                    id.setText(admin.adminId.toString())
                    name.setText(admin.adminName)
                    email.setText(admin.adminEmail)
                    if (admin.adminImage != null) {
                        image.setImageBitmap(
                            BitmapFactory.decodeByteArray(admin.adminImage, 0, admin.adminImage.size)
                        )
                    } else {
                        image.setImageDrawable(resources.getDrawable(R.drawable.add_img))
                    }
                }
            }
        }

        btn_update.setOnClickListener {
            if(validation()){
                Toast.makeText(this,"Profile Updated",Toast.LENGTH_SHORT).show()
                this.recreate()
            } else{
                Toast.makeText(this,"Something Went Wrong", Toast.LENGTH_SHORT).show()
            }
        }

        binding.topAppBar.setNavigationOnClickListener {
            startActivity(Intent(applicationContext, Admin_panel::class.java))
            finish()
        }
    }

    private fun validation(): Boolean {
        if(id.length() == 0){
            id.error = "Id Required"
            return false
        } else if(name.length()==0){
            name.error = "Name Required"
            return false
        } else if(email.length()==0){
            email.error = "Email Can't Be Empty"
            return false
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()){
            Toast.makeText(this,"Email Format Wrong !",Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}