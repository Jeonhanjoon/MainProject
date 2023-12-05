package com.example.mainproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mainproject.AccList
import com.example.mainproject.Things
import com.example.mainproject.repository.AccountRepository
import com.google.firebase.database.FirebaseDatabase

class AccountViewModel: ViewModel() {
    private val _balance=MutableLiveData<String>("0")
    private val _income=MutableLiveData<String>("0")
    private val _expense=MutableLiveData<String>("0")
    val balance : LiveData<String> get()=_balance
    val income : LiveData<String> get()=_income
    val expense : LiveData<String> get()=_expense
    private  val repository=AccountRepository()
    init{
        repository.observeBal(_balance)
    }
    val init=AccList()
    private val _acclist=MutableLiveData<AccList>(init)
    val acclist:LiveData<AccList> get() = _acclist
    fun addMoney(money:String,incType:Boolean?,expType:Boolean?){
        if(expType==true) _expense.value=(_expense.value?.toInt()?.plus(money.toInt())).toString()
        if(incType==true) _income.value=(_income.value?.toInt()?.plus(money.toInt())).toString()
        //repository.postBal(_balance.value?:"0")
    }


    private val _things=MutableLiveData<ArrayList<Things>?>()

    init{
        repository.observeUsers(_things) //레포지토리를 사용하여 데이터베이스에서 값 가져옴
    }

    val things : MutableLiveData<ArrayList<Things>?> get() =_things
    fun addUser(data : Things){
        repository.addUserData(data) //레포지토리를 사용하여 데이터베이스에 값 저장
    }
    fun deleteUser(data: Things) {
        repository.deleteUserData(data)
    }
    private val databaseReference = FirebaseDatabase.getInstance().getReference("users")

    fun deleteThing(thing: Things) {
        thing.id?.let { databaseReference.child(it).removeValue() }
    }
}
