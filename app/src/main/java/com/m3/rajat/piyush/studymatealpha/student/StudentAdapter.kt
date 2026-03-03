package com.m3.rajat.piyush.studymatealpha.student
import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.m3.rajat.piyush.studymatealpha.R
import com.m3.rajat.piyush.studymatealpha.database.StudentEntity

class StudentAdapter : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    private var stdList : ArrayList<StudentEntity> = ArrayList()
    private var onClickItem : ((StudentEntity) -> Unit) ?= null

    @SuppressLint("NotifyDataSetChanged")
    fun addItems(items: List<StudentEntity>){
        this.stdList = ArrayList(items)
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback: ((StudentEntity) -> Unit) ?= null){
        this.onClickItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= StudentViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.card_student_data,parent,false)
    )

    override fun getItemCount(): Int = stdList.size

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val std = stdList[position]
        holder.bindView(std)
        holder.itemView.setOnClickListener{ onClickItem?.invoke(std) }
    }

    class StudentViewHolder(var view: View): RecyclerView.ViewHolder(view) {
        private var name = view.findViewById<TextView>(R.id.StudentName)
        private var email = view.findViewById<TextView>(R.id.StudentEmailId)
        private var sclass = view.findViewById<TextView>(R.id.StudentClass)
        private var image = view.findViewById<ImageView>(R.id.UserAccountProfile)

        fun bindView(std : StudentEntity){
            val bitmap = std.studentImage?.let { BitmapFactory.decodeByteArray(it,0, it.size) }
            image.setImageBitmap(bitmap)
            name.text = std.studentName
            email.text = std.studentEmail
            sclass.text = std.studentClass
        }
    }
}