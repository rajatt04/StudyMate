package com.m3.rajat.piyush.studymatealpha.faculty
import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.m3.rajat.piyush.studymatealpha.R
import com.m3.rajat.piyush.studymatealpha.database.FacultyEntity

class FacultyAdapter : RecyclerView.Adapter<FacultyAdapter.FacultyViewHolder>() {

    private var facList : ArrayList<FacultyEntity> = ArrayList()
    private var onClickItem : ((FacultyEntity) -> Unit) ?= null

    @SuppressLint("NotifyDataSetChanged")
    fun addItems(items: List<FacultyEntity>){
        this.facList = ArrayList(items)
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback: ((FacultyEntity) -> Unit) ?= null){
        this.onClickItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= FacultyViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.card_faculty_data,parent,false)
    )

    override fun getItemCount(): Int = facList.size

    override fun onBindViewHolder(holder: FacultyViewHolder, position: Int) {
        val fac = facList[position]
        holder.bindView(fac)
        holder.itemView.setOnClickListener{ onClickItem?.invoke(fac) }
    }

    class FacultyViewHolder(var view: View): RecyclerView.ViewHolder(view) {
        private var image = view.findViewById<ImageView>(R.id.UserAccountProfile)
        private var name = view.findViewById<TextView>(R.id.FacultyName)
        private var email = view.findViewById<TextView>(R.id.FacultyEmail)
        private var subject = view.findViewById<TextView>(R.id.FacultySub)

        fun bindView(fac : FacultyEntity){
            val bitmap = fac.facultyImage?.let { BitmapFactory.decodeByteArray(it,0, it.size) }
            image.setImageBitmap(bitmap)
            name.text = fac.facultyName
            email.text = fac.facultyEmail
            subject.text = fac.facultySub
        }
    }
}