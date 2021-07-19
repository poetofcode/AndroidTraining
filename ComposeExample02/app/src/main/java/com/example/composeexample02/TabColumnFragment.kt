package com.example.composeexample02

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.composeexample02.model.TabColumnModel

class TabColumnFragment: Fragment() {
    private val model: TabColumnModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                Root()
            }
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    fun Root() {
        // val isReady by model.isReady.observeAsState(false)

        MaterialTheme {
            Text("Мияги рулит!")
        }
    }

}