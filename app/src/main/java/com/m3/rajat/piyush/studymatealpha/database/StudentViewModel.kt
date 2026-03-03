package com.m3.rajat.piyush.studymatealpha.database

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class StudentViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getInstance(application)
    private val studentDao = db.studentDao()

    private val _studentList = MutableLiveData<List<StudentEntity>>()
    val studentList: LiveData<List<StudentEntity>> = _studentList

    private val _operationResult = MutableLiveData<Boolean>()
    val operationResult: LiveData<Boolean> = _operationResult

    private val _lookupResult = MutableLiveData<List<StudentEntity>>()
    val lookupResult: LiveData<List<StudentEntity>> = _lookupResult

    fun insertStudent(student: StudentEntity) {
        viewModelScope.launch {
            try {
                studentDao.insert(student)
                _operationResult.postValue(true)
            } catch (e: Exception) {
                _operationResult.postValue(false)
            }
        }
    }

    fun getAllStudents() {
        viewModelScope.launch {
            _studentList.postValue(studentDao.getAll())
        }
    }

    fun deleteStudent(email: String) {
        viewModelScope.launch {
            studentDao.deleteByEmail(email)
            _studentList.postValue(studentDao.getAll())
        }
    }

    fun updateStudent(student: StudentEntity) {
        viewModelScope.launch {
            studentDao.update(student)
            _operationResult.postValue(true)
        }
    }

    fun isStudent(email: String) {
        viewModelScope.launch {
            _lookupResult.postValue(studentDao.getByEmail(email))
        }
    }

    fun getById(id: Int, callback: (StudentEntity?) -> Unit) {
        viewModelScope.launch {
            val results = studentDao.getById(id)
            callback(results.firstOrNull())
        }
    }
}
