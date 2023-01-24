package com.jvrni.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jvrni.filteroption.models.FilterEntity
import com.jvrni.filteroption.view.compose.FilterOption

class ComposeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Box(modifier = Modifier.fillMaxSize().padding(10.dp)) {
                FilterOption(
                    onClearFilter = {},
                    onSelectFilter = {},
                    borderColor = Color.Green.copy(alpha = 0.2f),
                    borderColorSelected = Color.Green,
                    backgroundColor = Color.White,
                    backgroundColorSelected = Color.Green.copy(alpha = 0.2f),
                    textColor = Color.Black,
                    textColorSelected = Color.Black,
                    filtersItems = listOf(
                        FilterEntity(
                            id = 0,
                            value = "First"
                        ),
                        FilterEntity(
                            id = 1,
                            value = "Second"
                        ),
                        FilterEntity(
                            id = 2,
                            value = "Third"
                        ),
                    )
                )
            }
        }
    }
}