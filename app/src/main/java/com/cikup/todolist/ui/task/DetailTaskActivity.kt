package com.cikup.todolist.ui.task

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.cikup.todolist.MainActivity
import com.cikup.todolist.R
import com.cikup.todolist.ui.login.LoginActivity
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_detail_task.*
import java.text.SimpleDateFormat
import java.util.*

class DetailTaskActivity : AppCompatActivity(), View.OnClickListener {

    private var status = false

    private var idTask = ""
    private var time = Date()


    private var firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_task)

        idTask = intent.getStringExtra("id").toString()

        deleteTaskBTN.setOnClickListener(this)
        updateTaskBTN.setOnClickListener(this)
        chooseDateTaskDetailEDT.setOnClickListener(this)


        completeCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            status = isChecked

        }
        Log.e("TAG", "status check: $status", )

        getDetailTask()
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.deleteTaskBTN ->{
                deleteTask()
            }
            R.id.updateTaskBTN ->{
                updateTask()
            }
            R.id.chooseDateTaskDetailEDT ->{
                chooseDate(this, chooseDateTaskDetailEDT)
            }
        }
    }

    private fun getDetailTask(){
        firestore.collection("TODO").document(Firebase.auth.currentUser!!.uid)
            .collection("TASK")
            .document(idTask)
            .get()
            .addOnCompleteListener {
                if (it.result != null){
                    val data = it.result!!

                    titleTaskDetailEDT.setText(data["title"].toString())
                    descriptionTaskDetailEDT.setText(data["description"].toString())

                    completeCheckBox.isChecked = data["status"] as Boolean

                    val dateTimeString = data["date"] as Timestamp

                    time = dateTimeString.toDate()
                    val myFormat = "MMM dd yyyy" //In which you need put here
                    val sdf = SimpleDateFormat(myFormat, Locale.ENGLISH)

                    chooseDateTaskDetailEDT.setText(sdf.format(dateTimeString.toDate()))


                }
            }
            .addOnFailureListener {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()

            }
    }

    private fun updateTask(){

        val title = titleTaskDetailEDT.text.toString()
        val description = descriptionTaskDetailEDT.text.toString()

        val data = hashMapOf<String, Any>(
            "id" to idTask,
            "title" to title,
            "description" to description,
            "status" to status,
            "date" to time
        )
        firestore.collection("TODO").document(Firebase.auth.currentUser!!.uid)
            .collection("TASK")
            .document(idTask)
            .update(data)
            .addOnCompleteListener {
                startActivity(Intent(this, MainActivity::class.java))
                Toast.makeText(this, "Update Success", Toast.LENGTH_SHORT).show()

            }
            .addOnFailureListener {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()

            }
    }

    private fun deleteTask(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete")
        builder.setMessage("Do You Want To Delete?")
        builder.setPositiveButton("Delete") { dialog, id ->
            firestore.collection("TODO").document(Firebase.auth.currentUser!!.uid)
                .collection("TASK")
                .document(idTask)
                .delete()
                .addOnCompleteListener {
                    startActivity(Intent(this, MainActivity::class.java))
                    Toast.makeText(this, "Delete Success", Toast.LENGTH_SHORT).show()

                }
                .addOnFailureListener {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()

                }
        }
        builder.setNegativeButton(
            "No") { dialog, id ->
        }
        builder.show()

    }

    private fun chooseDate(context: Context, dateString: EditText) {
        val calendar = Calendar.getInstance()
        val date =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val myFormat = "MMM dd yyyy" //In which you need put here
                val sdf = SimpleDateFormat(myFormat, Locale.ENGLISH)
                time = calendar.time
                val dateResult = sdf.format(calendar.time)
                dateString.setText(dateResult.toString())
            }

        DatePickerDialog(
            context, date, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }


}