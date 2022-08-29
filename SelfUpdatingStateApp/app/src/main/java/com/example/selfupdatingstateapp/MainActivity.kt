package com.example.selfupdatingstateapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private val uiStateUpdater: SelfReducer<UiState> = object : SelfReducer<UiState> {
        override fun reduceSelf(cb: UiState.() -> UiState) {
            uiState = uiState.cb()
        }
    }

    class TextFieldUpdater(
        private val id: Int,
        private val uiStateUpdater: SelfReducer<UiState>
    ) : SelfReducer<TextField> {
        override fun reduceSelf(cb: TextField.() -> TextField) = uiStateUpdater.reduceSelf {
            copy(
                fields = fields.map { field ->
                    if (field.id == id) field.cb() else field
                }
            )
        }
    }

    private var uiState = UiState(
        updater = uiStateUpdater,
        fields = listOf(
            TextField(
                updater = TextFieldUpdater(0, uiStateUpdater),
                id = 0,
                text = "Ivan Petrov",
                isEnabled = false
            ),
            TextField(
                updater = TextFieldUpdater(1, uiStateUpdater),
                id = 1,
                text = "Nikolay Drozdov",
                isEnabled = false
            )
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionButton: Button = findViewById(R.id.action_button)
        actionButton.setOnClickListener {
            onActionButtonClick()
            render()
        }
        render()
    }

    private fun onActionButtonClick() {
        uiState.fields[1].reduceSelf { copy(isEnabled = true) }
    }

    private fun render() {
        val contentTextView: TextView = findViewById(R.id.screen_content)
        contentTextView.text = uiState.fields.joinToString("\n") {
            "${it.text}: ${it.isEnabled}"
        }
    }
}