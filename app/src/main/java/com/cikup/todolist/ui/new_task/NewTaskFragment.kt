package com.cikup.todolist.ui.new_task

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.cikup.todolist.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_new_task.*
import java.text.SimpleDateFormat
import java.util.*

class NewTaskFragment : Fragment(), View.OnClickListener {

    private val firestore = FirebaseFirestore.getInstance()

    private var time = Date()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new_task, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chooseDateTaskEDT.setOnClickListener(this)
        createTaskBTN.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.chooseDateTaskEDT ->{
                chooseDate(requireContext(), dateString = chooseDateTaskEDT)
            }
            R.id.createTaskBTN ->{
                createNewTask()
            }
        }
    }

    private fun createNewTask(){
        val title = titleTaskEDT.text.toString()
        val description = descriptionTaskEDT.text.toString()

        val customId = firestore.collection("TODO").document().id

        val data = hashMapOf<String, Any>(
            "id" to customId,
            "title" to title,
            "description" to description,
            "status" to false,
            "date" to time,
        )

        firestore.collection("TODO").document(Firebase.auth.currentUser!!.uid)
            .collection("TASK")
            .document(customId)
            .set(data)
            .addOnCompleteListener {
                Toast.makeText(requireContext(), "Create Task Success", Toast.LENGTH_SHORT).show()

            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Failed : ${it.message}", Toast.LENGTH_SHORT).show()

            }

    }



    fun chooseDate(context: Context, dateString:EditText) {
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