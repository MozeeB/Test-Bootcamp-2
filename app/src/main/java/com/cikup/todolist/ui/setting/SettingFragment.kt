package com.cikup.todolist.ui.setting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.cikup.todolist.R
import com.cikup.todolist.ui.login.LoginActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_setting.*

class SettingFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logoutBTN.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Sign Out")
            builder.setMessage("Do You Want To Sign Out?")
            builder.setPositiveButton("Sign Out") { dialog, id ->
                Firebase.auth.signOut()
                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or
                            Intent.FLAG_ACTIVITY_NEW_TASK
                )
                startActivity(intent)

            }
            builder.setNegativeButton(
                "No"
            ) { dialog, id ->
            }
            builder.show()
        }
    }

}