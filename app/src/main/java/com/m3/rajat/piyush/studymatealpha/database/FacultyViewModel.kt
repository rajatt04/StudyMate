package com.m3.rajat.piyush.studymatealpha.database

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class FacultyViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getInstance(application)
    private val facultyDao = db.facultyDao()

    private val _facultyList = MutableLiveData<List<FacultyEntity>>()
    val facultyList: LiveData<List<FacultyEntity>> = _facultyList

    private val _operationResult = MutableLiveData<Boolean>()
    val operationResult: LiveData<Boolean> = _operationResult

    private val _lookupResult = MutableLiveData<List<FacultyEntity>>()
    val lookupResult: LiveData<List<FacultyEntity>> = _lookupResult

    fun insertFaculty(faculty: FacultyEntity) {
        viewModelScope.launch {
            try {
                facultyDao.insert(faculty)
                _operationResult.postValue(true)
            } catch (e: Exception) {
                _operationResult.postValue(false)
            }
        }
    }

    fun getAllFaculty() {
        viewModelScope.launch {
            _facultyList.postValue(facultyDao.getAll())
        }
    }

    fun deleteFaculty(email: String) {
        viewModelScope.launch {
            facultyDao.deleteByEmail(email)
            _facultyList.postValue(facultyDao.getAll())
        }
    }

    fun updateFaculty(faculty: FacultyEntity) {
        viewModelScope.launch {
            facultyDao.update(faculty)
            _operationResult.postValue(true)
        }
    }

    fun isFaculty(email: String) {
        viewModelScope.launch {
            _lookupResult.postValue(facultyDao.getByEmail(email))
        }
    }

    fun getById(id: Int, callback: (FacultyEntity?) -> Unit) {
        viewModelScope.launch {
            val results = facultyDao.getById(id)
            callback(results.firstOrNull())
        }
    }
}
