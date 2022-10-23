package com.example.flow

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.database.db.MovesForTable
import com.reza.sqlliteapp.entities.User

class Setting : AppCompatActivity() {
    private var userId: Int = 0
    private lateinit var movesForTable: MovesForTable
    lateinit var userEmail: TextView
    lateinit var firstName: TextView
    lateinit var lastName: TextView
    lateinit var userEmailEditText: EditText
    lateinit var userFirstNameEditText: EditText
    lateinit var userLastNameEditText: EditText
    lateinit var update_button: Button
    lateinit var getUser: MutableList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        movesForTable = MovesForTable(this)
        userEmail = findViewById(R.id.setting_email)
        firstName = findViewById(R.id.setting_firstName)
        lastName = findViewById(R.id.setting_lastName)
        userEmailEditText = findViewById(R.id.setting_emailEdit)
        userFirstNameEditText = findViewById(R.id.setting_firstNameEditText)
        userLastNameEditText = findViewById(R.id.setting_lastNameEditText)

        update_button = findViewById(R.id.update_button)

        val sharedPref = getSharedPreferences("GLOBAL", Context.MODE_PRIVATE) ?: return
        userId = sharedPref.getInt("USER_ID", 0)

        getUser = movesForTable.getUser(userId)
        for (user in getUser) {
            userEmail.setText(user.email)
            userEmailEditText.setText(user.email)
            firstName.setText(user.firstName)
            userFirstNameEditText.setText(user.firstName)
            lastName.setText(user.lastName)
            userLastNameEditText.setText(user.lastName)
        }

        update_button.setOnClickListener {
            var getSingleUser = movesForTable.getSingleUser(userId)
            if (getSingleUser != null) {
                getSingleUser.email = userEmailEditText.text.toString()
                getSingleUser.firstName = userFirstNameEditText.text.toString()
                getSingleUser.lastName = userLastNameEditText.text.toString()

                Log.e("", getSingleUser.email)
            }
            if (getSingleUser != null) {
                if (movesForTable.update(getSingleUser)) {
                    Toast.makeText(this, "Information Updated", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}