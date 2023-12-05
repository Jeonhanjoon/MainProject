package com.example.mainproject.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mainproject.Things
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import java.time.LocalDate

class AccountRepository {
    val database = Firebase.database
    val balRef = database.getReference("balance")
    fun observeBal(bal:MutableLiveData<String>){
        balRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                bal.postValue(snapshot.value.toString())
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
    fun postBal(newValue:String){
        balRef.setValue(newValue)
    }

     val userRef = database.getReference("user")

    fun observeUsers(usersData: MutableLiveData<ArrayList<Things>?>) {
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val users = ArrayList<Things>()
                for (snap in snapshot.children) {
                    val user = snap.getValue<Things>()
                    user?.let {
                        users.add(it)
                    }
                }
                usersData?.postValue(users)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled if needed
            }
        })
    }

    fun addUserData(data: Things) {
        userRef.push().setValue(data)
    }

    fun deleteUserData(data: Things) {
        // Find the reference of the data to be deleted
        val query = userRef.orderByChild("id").equalTo(data.name)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (childSnapshot in snapshot.children) {
                    // Remove the data from the database
                    childSnapshot.ref.removeValue()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled if needed
            }
        })
    }
 /*   @RequiresApi(Build.VERSION_CODES.O)
    private val _currentDate = MutableLiveData(LocalDate.now().withDayOfMonth(1))
    @RequiresApi(Build.VERSION_CODES.O)
    val currentDate: LiveData<LocalDate> = _currentDate

    @RequiresApi(Build.VERSION_CODES.O)
    fun setYear() {
        _currentDate.value = _currentDate.value?.withDayOfYear(1)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setMonth() {
        _currentDate.value = _currentDate.value?.withDayOfMonth(1)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun prevMonth() {
        _currentDate.value = _currentDate.value?.minusMonths(1)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun nextMonth() {
        _currentDate.value = _currentDate.value?.plusMonths(1)
    }*/
 /*   fun deleteThing(thing: Things) {
        database.collection("users").document(thing.name)
            .delete()
            .addOnSuccessListener {
                fetchThings()  // refresh the data after delete
            }
    }

*/



}