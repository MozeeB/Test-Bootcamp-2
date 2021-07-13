package com.cikup.todolist.ui.task

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cikup.todolist.R
import com.cikup.todolist.adapter.AdapterTask
import com.cikup.todolist.model.TaskModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_task.*
import java.util.*
import kotlin.collections.ArrayList

class TaskFragment : Fragment() {


    private val firestore = FirebaseFirestore.getInstance()

    private var taskList: ArrayList<TaskModel> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_task, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getCurrentUser()
    }

    @SuppressLint("SetTextI18n")
    private fun getCurrentUser() {
       val user =  Firebase.auth.currentUser
        user.let {
            usernameTV.text = "Hallo, ${user?.displayName}!"
            Picasso.get().load(user?.photoUrl).into(imageViewProfile)

            getTasks()
        }
    }

    private var resultTask = 0
    @SuppressLint("SetTextI18n")
    private fun getTasks() {
        resultTask = 0
        firestore.collection("TODO").document(Firebase.auth.currentUser!!.uid)
            .collection("TASK")
            .get()
            .addOnCompleteListener {
                if (it.result != null) {
                    val data = it.result!!
                    data.forEach {
                        if (it["status"] as Boolean){
                            taskList.add(
                                TaskModel(
                                    it["id"].toString(),
                                    it["title"].toString(),
                                    it["description"].toString(),
                                    it["status"] as Boolean,
                                    it["date"] as Timestamp
                                )
                            )
                        }else{
                            resultTask += 1
                            taskList.add(
                                TaskModel(
                                    it["id"].toString(),
                                    it["title"].toString(),
                                    it["description"].toString(),
                                    it["status"] as Boolean,
                                    it["date"] as Timestamp
                                )
                            )
                        }
                    }

                    taskResultTV.text = "$resultTask Task for Today"

                    taskRV.apply {
                        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                        adapter = AdapterTask(taskList)
                    }
                }
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            }
    }

}