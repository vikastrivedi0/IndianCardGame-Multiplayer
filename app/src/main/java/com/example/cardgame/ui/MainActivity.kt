package com.example.cardgame.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cardgame.R

class MainActivity : AppCompatActivity() {

    private lateinit var codeEditText: EditText
    private lateinit var createRoomButton: Button
    private lateinit var joinRoomButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        codeEditText = findViewById<EditText>(R.id.RoomCodeTextView)
        createRoomButton = findViewById<Button>(R.id.createRoom)
        joinRoomButton = findViewById<Button>(R.id.joinRoom)

        createRoomButton.setOnClickListener {
            codeEditText.visibility = View.VISIBLE
            codeEditText.isFocusable = false
            codeEditText.isClickable = false
            codeEditText.isLongClickable = false

            val roomCode = generateRoomCode()
            codeEditText.setText(roomCode)
        }

        joinRoomButton.setOnClickListener {}

    }

    private fun generateRoomCode(): String {
        val allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val code = StringBuilder()
        for (i in 0 until 4) {
            code.append(allowedChars.random())
        }
        return code.toString()

    }
}