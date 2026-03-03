package com.m3.rajat.piyush.studymatealpha.database

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class AdminViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getInstance(application)
    private val adminDao = db.adminDao()

    private val _loginResult = MutableLiveData<AdminEntity?>()
    val loginResult: LiveData<AdminEntity?> = _loginResult

    private val _operationResult = MutableLiveData<Boolean>()
    val operationResult: LiveData<Boolean> = _operationResult

    fun insertAdmin(admin: AdminEntity) {
        viewModelScope.launch {
            try {
                adminDao.insert(admin)
                _operationResult.postValue(true)
            } catch (e: Exception) {
                _operationResult.postValue(false)
            }
        }
    }

    fun checkAdmin(email: String) {
        viewModelScope.launch {
            val results = adminDao.getByEmail(email)
            _loginResult.postValue(results.firstOrNull())
        }
    }

    fun updateImage(email: String, image: ByteArray) {
        viewModelScope.launch {
            adminDao.updateImage(email, image)
            _operationResult.postValue(true)
        }
    }

    fun getAdminById(id: Int, callback: (AdminEntity?) -> Unit) {
        viewModelScope.launch {
            val results = adminDao.getById(id)
            callback(results.firstOrNull())
        }
    }
}
