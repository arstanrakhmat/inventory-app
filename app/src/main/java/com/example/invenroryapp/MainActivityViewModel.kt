package com.example.invenroryapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.invenroryapp.db.RoomAppDb
import com.example.invenroryapp.db.UserEntity

class MainActivityViewModel(app: Application): AndroidViewModel(app) {
    var allUsers: MutableLiveData<List<UserEntity>> = MutableLiveData()

    init {
        getAllUsers()
    }

    fun getAllUsersObservers(): MutableLiveData<List<UserEntity>> {
        return allUsers
    }

    fun getAllUsers() {
        val userDao = RoomAppDb.getAppDataBase((getApplication()))?.userDao()
        val list = userDao?.getAllUserInfo()

        allUsers.postValue(list)
    }

    fun insertUserInfo(entity: UserEntity) {
        val userDao = RoomAppDb.getAppDataBase(getApplication())?.userDao()
        userDao?.insertUser(entity)
        getAllUsers()
    }

    fun updateUserInfo(entity: UserEntity) {
        val userDao = RoomAppDb.getAppDataBase(getApplication())?.userDao()
        userDao?.updateUser(entity)
        getAllUsers()
    }

    fun deleteUserInfo(entity: UserEntity) {
        val userDao = RoomAppDb.getAppDataBase(getApplication())?.userDao()
        userDao?.deleteUser(entity)
        getAllUsers()
    }
}