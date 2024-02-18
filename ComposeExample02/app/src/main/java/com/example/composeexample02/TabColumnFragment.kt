package com.example.composeexample02

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.composeexample02.model.TabColumnModel
import com.example.composeexample02.skeleton.Skeleton

class TabColumnFragment : Fragment() {
    private val model: TabColumnModel by activityViewModels()

    @ExperimentalMaterialApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme { Root(model) }
            }
        }
    }

    @ExperimentalMaterialApi
    @Preview(showSystemUi = true)
    @Composable
    fun DefaultPreview() {
        Root(null)
    }

    @ExperimentalMaterialApi
    @Composable
    fun Root(model: TabColumnModel?) {
        val listState = model?.currentUnits?.observeAsState(emptyList())

        Skeleton.TabLazyColumn(
            modifier = Modifier.padding(8.dp),
            isLoading = false,
            tabs = model?.tabs ?: listOf("Tab 1", "Tab 2"),
            selected = 0,
            onSelect = { model?.onSelect(it) },
            tabView = { title, isSelected ->
                Column(
                    modifier = Modifier
                        .height(50.dp)
                        .background(if (!isSelected) Color.LightGray else Color.Transparent)
                        .border(
                            width = 4.dp,
                            brush = SolidColor(Color.LightGray),
                            shape = RoundedCornerShape(0.dp)
                        ),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = title
                        // modifier = Modifier.padding(16.dp)
                    )
                }
            }
        ) {
            listState?.value?.forEach {
                item {
                    Text(it)
                }
            }
        }
    }

}