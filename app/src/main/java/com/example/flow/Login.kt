package com.example.flow

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.database.db.MovesForTable
import com.google.android.material.textfield.TextInputEditText

class Login : AppCompatActivity() {
    private lateinit var loginRegisterOutlinedButton: Button
    private lateinit var loginLoginOutlinedButton: Button
    private lateinit var loginEmailIDEditText: TextInputEditText
    private lateinit var loginPasswordEditText: TextInputEditText
    private lateinit var movesForTable: MovesForTable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginEmailIDEditText = findViewById(R.id.login_emailID_EditText)
        loginPasswordEditText = findViewById(R.id.login_password_EditText)

        loginLoginOutlinedButton = findViewById(R.id.Login_Login_outlinedButton)
        loginLoginOutlinedButton.setOnClickListener(this::onClickLogin)

        loginRegisterOutlinedButton = findViewById(R.id.Login_Register_outlinedButton)
        loginRegisterOutlinedButton.setOnClickListener(this::onClickRegistration)
    }

    private fun onClickRegistration(view: View) {
        val intent = Intent(this, Register::class.java)
        startActivity(intent)
    }

    private fun onClickLogin(view: View) {
        movesForTable = MovesForTable(this)
        if (loginEmailIDEditText.text.toString()
                .isEmpty() || loginPasswordEditText.text.toString().isEmpty()
        ) {
            Toast.makeText(this, "Give all the information", Toast.LENGTH_SHORT).show()
        } else {
            val user = movesForTable.authenticate(
                loginEmailIDEditText.text.toString(),
                loginPasswordEditText.text.toString()
            )
            if (user != null
            ) {
                // save ID/email in shared preference
                val sharedPref = getSharedPreferences("GLOBAL", Context.MODE_PRIVATE) ?: return
                with (sharedPref.edit()) {
                    putInt("USER_ID",  user.id)
                    commit()
                }

                val intent = Intent(this, Home::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Email or Password is wrong", Toast.LENGTH_SHORT).show()
            }
        }

    }
}