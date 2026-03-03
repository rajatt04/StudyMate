package com.m3.rajat.piyush.studymatealpha.database

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class NoticeViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getInstance(application)
    private val noticeDao = db.noticeDao()

    private val _noticeList = MutableLiveData<List<NoticeEntity>>()
    val noticeList: LiveData<List<NoticeEntity>> = _noticeList

    private val _operationResult = MutableLiveData<Boolean>()
    val operationResult: LiveData<Boolean> = _operationResult

    fun insertNotice(notice: NoticeEntity) {
        viewModelScope.launch {
            noticeDao.insert(notice)
            _operationResult.postValue(true)
        }
    }

    fun getAllNotices() {
        viewModelScope.launch {
            _noticeList.postValue(noticeDao.getAll())
        }
    }

    fun deleteNotice(name: String) {
        viewModelScope.launch {
            noticeDao.deleteByName(name)
            _noticeList.postValue(noticeDao.getAll())
        }
    }
}
