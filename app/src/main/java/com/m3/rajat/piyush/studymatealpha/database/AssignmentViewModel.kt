package com.m3.rajat.piyush.studymatealpha.database

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class AssignmentViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getInstance(application)
    private val assignmentDao = db.assignmentDao()

    private val _assignmentList = MutableLiveData<List<AssignmentEntity>>()
    val assignmentList: LiveData<List<AssignmentEntity>> = _assignmentList

    private val _operationResult = MutableLiveData<Boolean>()
    val operationResult: LiveData<Boolean> = _operationResult

    fun insertAssignment(assignment: AssignmentEntity) {
        viewModelScope.launch {
            assignmentDao.insert(assignment)
            _operationResult.postValue(true)
        }
    }

    fun getAllAssignments() {
        viewModelScope.launch {
            _assignmentList.postValue(assignmentDao.getAll())
        }
    }
}
