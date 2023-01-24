package com.jvrni.example;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.jvrni.filteroption.databinding.ActivityMainBinding;
import com.jvrni.filteroption.models.FilterEntity;

import java.util.ArrayList;
import kotlin.Unit;

public class JavaActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayList<FilterEntity> list = new ArrayList<>();
        list.add(new FilterEntity(0, "First"));
        list.add(new FilterEntity(1, "Second"));
        list.add(new FilterEntity(2, "Third"));

        binding.filterOption.setAdapter(
                list,
                this::onCLose,
                this::onAction
        );
    }

    Unit onCLose() {
        return null;
    }

    Unit onAction(FilterEntity item) {
        Toast.makeText(this, item.getValue(), Toast.LENGTH_SHORT).show();
        return null;
    }
}