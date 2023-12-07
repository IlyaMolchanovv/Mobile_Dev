package com.example.list1110.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.list1110.data.Faculty
import com.example.list1110.data.Group
import com.example.list1110.data.ListOfFaculty
import com.example.list1110.data.Student
import com.example.list1110.repository.AppRepository
import java.util.Date

class StudentsViewModel : ViewModel() {
    var studentList : MutableLiveData<List<Student>> = MutableLiveData()
    private var _student: Student? = null
    val student
        get()= _student
    private lateinit var group : Group
    fun setGroup(group: Group){
        this.group=group
        AppRepository.getInstance().listOfStudent.observeForever {
            studentList.postValue(AppRepository.getInstance().getGroupStudents(group.id))
        }
        AppRepository.getInstance().student.observeForever {
            _student=it
        }
    }
    fun deleteStudent(){
        if(student!=null)
            AppRepository.getInstance().deleteStudent(student!!)
    }

    fun appendStudent(lastName: String, firstName: String, middleName:String, birthDate: Date){
        val student=Student()
        student.lastName=lastName
        student.firstName=firstName
        student.middleName=middleName
        student.birthDate=birthDate
        student.groupID=group.id
        AppRepository.getInstance().updateStudent(student)
    }


    fun updateStudent(lastName: String, firstName: String, middleName:String, birthDate: Date) {
        if (_student != null) {
            _student!!.lastName=lastName
            _student!!.firstName=firstName
            _student!!.middleName=middleName
            _student!!.birthDate=birthDate
            AppRepository.getInstance().updateStudent(_student!!)
        }
    }
    fun setCurrentStudent(student: Student){
        AppRepository.getInstance().setCurrentStudent(student)
    }
}