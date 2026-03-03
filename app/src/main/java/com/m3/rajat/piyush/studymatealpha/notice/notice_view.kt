package com.m3.rajat.piyush.studymatealpha.notice
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.color.MaterialColors
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.m3.rajat.piyush.studymatealpha.R
import com.m3.rajat.piyush.studymatealpha.admin.Admin_panel
import com.m3.rajat.piyush.studymatealpha.database.NoticeViewModel
import com.m3.rajat.piyush.studymatealpha.databinding.ActivityNoticeViewBinding

@Suppress("DEPRECATION")
class notice_view : AppCompatActivity() {
    private lateinit var viewModel: NoticeViewModel
    private lateinit var recyclerView : RecyclerView
    private var adapter : NoticeAdapter?= null
    private lateinit var binding : ActivityNoticeViewBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoticeViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[NoticeViewModel::class.java]
        initRecyclerView()

        viewModel.noticeList.observe(this) { list ->
            adapter?.addItems(list)
            if (list.isEmpty()) showEmptyState()
        }

        viewModel.getAllNotices()

        adapter?.setOnClickItem { notice ->
            MaterialAlertDialogBuilder(this)
                .setTitle("Notice")
                .setMessage("Do You Want To Delete This Notice ?")
                .setCancelable(true)
                .setPositiveButton("Yes") { dialog,_ ->
                    viewModel.deleteNotice(notice.noticeName)
                    dialog.dismiss()
                }
                .setNegativeButton("No"){ dialog,_ -> dialog.dismiss() }
                .create().show()
        }

        binding.topAppBar.setNavigationOnClickListener {
            startActivity(Intent(applicationContext, Admin_panel::class.java))
            finish()
        }
    }

    private fun showEmptyState() {
        val dynamicColor = MaterialColors.getColor(
            this, com.google.android.material.R.attr.colorPrimary, Color.BLACK
        )
        val textView = TextView(this).apply {
            id = View.generateViewId()
            text = "Please Add Some \n Records First"
            textSize = 24f
            setTextColor(dynamicColor)
            gravity = Gravity.CENTER
        }
        val button = MaterialButton(this).apply {
            id = View.generateViewId()
            text = "Add Now"
            setBackgroundColor(dynamicColor)
            setTextColor(Color.WHITE)
            setOnClickListener { startActivity(Intent(applicationContext, notice_add::class.java)) }
        }
        val layoutParamsText = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            topToTop = ConstraintLayout.LayoutParams.PARENT_ID
            bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
            startToStart = ConstraintLayout.LayoutParams.PARENT_ID
            endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
        }
        val layoutParamsButton = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            topToBottom = textView.id
            startToStart = ConstraintLayout.LayoutParams.PARENT_ID
            endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
            topMargin = 32
        }
        binding.con.addView(textView, layoutParamsText)
        binding.con.addView(button, layoutParamsButton)
    }

    private fun initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerViewNotice)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = NoticeAdapter()
        recyclerView.adapter = adapter
    }
}