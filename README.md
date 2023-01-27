# Filter Option
Filter Option is a custom inline filter component built for Xml and Compose.


## Example

<img src="https://user-images.githubusercontent.com/20356438/215181530-37b3eacf-7252-4186-ac09-ecf8a1c5661f.gif" width="300" height="50">

## Contributions
Contributions are welcome, bugs and features should be reported by issues in this repository.

## How to use

- **Gradle** 
```
implementation 'io.github.jvrni:filteroption:0.0.2'
```

- **Compose**
```javascript
...

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
```

- **Xml**
```javascript
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <com.jvrni.filteroption.view.FilterOption
        android:id="@+id/filterOption"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        app:layout_constraintEnd_toEndOf="parent"
        app:colorBackground="@color/white"
        app:colorBackgroundSelected="@color/orange_60"
        app:borderColor="@color/orange"
        app:borderColorSelected="@color/orange"
        app:textColor="@color/black"
        app:textColorSelected="@color/white"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
</androidx.constraintlayout.widget.ConstraintLayout>
```



- **Xml - Kotlin**
```javascript
...

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
```


- **Xml - Java**
```javascript
...

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
```

## License
```
Copyright (c) 2023 Jvrni

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
