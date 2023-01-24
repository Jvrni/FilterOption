package com.jvrni.example

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jvrni.filteroption.databinding.ActivityMainBinding
import com.jvrni.filteroption.models.FilterEntity

class KotlinActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.filterOption.setAdapter(
            list = listOf(
                FilterEntity(0, "First"),
                FilterEntity(1, "Second"),
                FilterEntity(2, "Third")
            ),
            onClearFilter = {},
            onSelectFilter = {
                Toast.makeText(this, it.value, Toast.LENGTH_SHORT).show()
            }
        )
    }
}