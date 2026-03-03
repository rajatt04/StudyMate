package com.m3.rajat.piyush.studymatealpha.notice
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.m3.rajat.piyush.studymatealpha.R
import com.m3.rajat.piyush.studymatealpha.database.NoticeEntity

class NoticeAdapter : RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder>() {

    private var noticeList : ArrayList<NoticeEntity> = ArrayList()
    private var onClickItem : ((NoticeEntity) -> Unit) ?= null

    @SuppressLint("NotifyDataSetChanged")
    fun addItems(items: List<NoticeEntity>){
        this.noticeList = ArrayList(items)
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback: ((NoticeEntity) -> Unit) ?= null){
        this.onClickItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= NoticeViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.card_notice_data,parent,false)
    )

    override fun getItemCount(): Int = noticeList.size

    override fun onBindViewHolder(holder: NoticeViewHolder, position: Int) {
        val notice = noticeList[position]
        holder.bindView(notice)
        holder.itemView.setOnClickListener{ onClickItem?.invoke(notice) }
    }

    class NoticeViewHolder(var view: View): RecyclerView.ViewHolder(view) {
        private var name = view.findViewById<TextView>(R.id.view_notice_name)
        private var des = view.findViewById<TextView>(R.id.view_notice_des)
        private var date = view.findViewById<TextView>(R.id.view_notice_date)

        fun bindView(notice : NoticeEntity){
            name.text = notice.noticeName
            des.text = notice.noticeDes
            date.text = notice.noticeDate
        }
    }
}