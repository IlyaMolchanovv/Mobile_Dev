package com.example.list10.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.list1110.data.Group
import com.example.list1110.repository.AppRepository
import java.text.FieldPosition
import java.util.Date

class GroupViewModel : ViewModel() {
    var groupList : MutableLiveData<List<Group>> = MutableLiveData()
    private var _group: Group? = null
    val group
        get() = _group

    init {
        AppRepository.getInstance().listOfGroup.observeForever{
            groupList.postValue(AppRepository.getInstance().facultyGroups)
        }
        AppRepository.getInstance().group.observeForever{
            _group=it
        }
        AppRepository.getInstance().faculty.observeForever{
            groupList.postValue(AppRepository.getInstance().facultyGroups)
        }
    }

    fun deleteGroup(){
        if (group!=null)
            AppRepository.getInstance().deleteGroup(group!!)
    }

    fun appendGroup(groupName: String){
        val group= Group()
        group.name =groupName
        group.facultyID=faculty?.id
        AppRepository.getInstance().updateGroup(group)
    }

    fun updateGroup(groupName: String){
        if (_group!=null){
            _group!!.name=groupName
            AppRepository.getInstance().updateGroup(_group!!)
        }
    }

    fun setCurrentGroup(position: Int){
        if ((groupList.value?.size ?: 0) > position)
            groupList.value?.let { AppRepository.getInstance().setCurrentGroup(it.get(position)) }
    }

    fun setCurrentGroup(group : Group){
        AppRepository.getInstance().setCurrentGroup(group)
    }

    val faculty
        get() = AppRepository.getInstance().faculty.value

    val groupListPosition
        get() = groupList.value?.indexOfFirst { it.id==group?.id } ?:-1
}