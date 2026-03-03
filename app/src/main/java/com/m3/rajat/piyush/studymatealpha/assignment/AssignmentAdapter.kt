package com.m3.rajat.piyush.studymatealpha.assignment
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.m3.rajat.piyush.studymatealpha.database.AssignmentEntity
import com.m3.rajat.piyush.studymatealpha.R

class AssignmentAdapter : RecyclerView.Adapter<AssignmentAdapter.AssignmentViewHolder>() {

    private var assignmentList : ArrayList<AssignmentEntity> = ArrayList()
    private var onClickItem : ((AssignmentEntity) -> Unit) ?= null

    @SuppressLint("NotifyDataSetChanged")
    fun addItems(items: List<AssignmentEntity>){
        this.assignmentList = ArrayList(items)
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback: ((AssignmentEntity) -> Unit) ?= null){
        this.onClickItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= AssignmentViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.card_assignment_data,parent,false)
    )

    override fun getItemCount(): Int = assignmentList.size

    override fun onBindViewHolder(holder: AssignmentViewHolder, position: Int) {
        val assignment = assignmentList[position]
        holder.bindView(assignment)
        holder.itemView.setOnClickListener{ onClickItem?.invoke(assignment) }
    }

    class AssignmentViewHolder(var view: View): RecyclerView.ViewHolder(view) {
        private var name = view.findViewById<TextView>(R.id.view_assignment_name)
        private var sdate = view.findViewById<TextView>(R.id.view_assignment_date)
        private var stype = view.findViewById<TextView>(R.id.view_assignment_type)

        fun bindView(assignment : AssignmentEntity){
            name.text = assignment.assignmentName
            sdate.text = assignment.assignmentSdate
            stype.text = assignment.assignmentType
        }
    }
}
