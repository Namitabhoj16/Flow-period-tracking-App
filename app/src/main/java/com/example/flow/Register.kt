package com.example.flow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.database.db.MovesForTable
import com.google.android.material.textfield.TextInputEditText

class Register : AppCompatActivity() {
    private lateinit var registerFirstNameEditText: TextInputEditText
    private lateinit var registerLastNameEditText: TextInputEditText
    private lateinit var registerEmailIDEditText: TextInputEditText
    private lateinit var registerPasswordEditText: TextInputEditText

    private lateinit var registerOutlinedButton: Button
    private lateinit var movesForTable: MovesForTable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        registerFirstNameEditText = findViewById(R.id.register_FirstName_EditText)
        registerLastNameEditText = findViewById(R.id.register_LastName_EditText)
        registerEmailIDEditText = findViewById(R.id.register_emailID_EditText)
        registerPasswordEditText = findViewById(R.id.register_password_EditText)
        registerOutlinedButton = findViewById(R.id.Login_Register_outlinedButton)

        registerOutlinedButton.setOnClickListener(this::onClick)
    }

    private fun onClick(view: View) {
        movesForTable = MovesForTable(this)
        Log.e("email", registerEmailIDEditText.text.toString())
        if (movesForTable.existingEmail(registerEmailIDEditText.text.toString())) {
            Toast.makeText(this, "Email Already Exist!", Toast.LENGTH_SHORT).show()
        } else {
            if (registerEmailIDEditText.text.toString()
                    .isEmpty() || registerPasswordEditText.text.toString()
                    .isEmpty() || registerFirstNameEditText.text.toString()
                    .isEmpty() || registerLastNameEditText.text.toString().isEmpty()
            ) {
                Toast.makeText(this, "Give all the information", Toast.LENGTH_SHORT).show()
            } else {
                movesForTable.insertData(
                    registerEmailIDEditText.text.toString(),
                    registerPasswordEditText.text.toString(),
                    registerFirstNameEditText.text.toString(),
                    registerLastNameEditText.text.toString()
                )
                Toast.makeText(this, "Registration DONE!", Toast.LENGTH_SHORT).show()
            }
        }

    }

}