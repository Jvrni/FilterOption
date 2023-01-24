package com.jvrni.filteroption.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.jvrni.filteroption.R
import com.jvrni.filteroption.adapter.FilterOptionAdapter
import com.jvrni.filteroption.databinding.FilterOptionBinding
import com.jvrni.filteroption.models.Item
import com.jvrni.filteroption.models.FilterEntity
import com.jvrni.filteroption.models.ItemState
import kotlinx.coroutines.flow.MutableStateFlow


class FilterOption : ConstraintLayout {
    private val binding: FilterOptionBinding =
        FilterOptionBinding.inflate(LayoutInflater.from(context), this, true)

    private var borderColor: Int = 0
    private var borderColorSelected: Int = 0
    private var backgroundColorItem: Int = 0
    private var backgroundColorItemSelected: Int = 0
    private var textColor: Int = 0
    private var textColorSelected: Int = 0

    constructor(context: Context) : super(context) {
        setupView()
    }

    constructor(
        context: Context,
        attrs: AttributeSet
    ) : super(context, attrs) {
        setupView(attrs)
    }

    private fun setupView(attrs: AttributeSet? = null) {
        attrs?.let { attributeSet ->
            context.obtainStyledAttributes(
                attributeSet,
                R.styleable.FilterOption, 0, 0
            ).run {
                borderColor = resources.getColor(this.getResourceId(R.styleable.FilterOption_borderColor, R.color.teal_200), resources.newTheme())
                borderColorSelected = resources.getColor(this.getResourceId(R.styleable.FilterOption_borderColorSelected, R.color.teal_700), resources.newTheme())
                backgroundColorItem = resources.getColor(this.getResourceId(R.styleable.FilterOption_colorBackground, R.color.purple_200), resources.newTheme())
                backgroundColorItemSelected = resources.getColor(this.getResourceId(R.styleable.FilterOption_colorBackgroundSelected, R.color.purple_700), resources.newTheme())
                textColor = resources.getColor(this.getResourceId(R.styleable.FilterOption_textColor, R.color.black), resources.newTheme())
                textColorSelected = resources.getColor(this.getResourceId(R.styleable.FilterOption_textColorSelected, R.color.black), resources.newTheme())

                recycle()
            }
        }
    }

    fun setAdapter(
        list: List<FilterEntity>,
        onClearFilter: () -> Unit,
        onSelectFilter: (FilterEntity) -> Unit
    ) {
        val newList = MutableStateFlow(list.map { Item(it) })

        binding.recycler.adapter = FilterOptionAdapter(
            list = newList,
            borderColor,
            borderColorSelected,
            backgroundColorItem,
            backgroundColorItemSelected,
            textColor,
            textColorSelected,
            onClearFilter = {
                onClearFilter.invoke()
                newList.value = list.map { Item(it) }
            },
            onSelectFilter = { item ->
                onSelectFilter.invoke(item)
                newList.value = newList.value.map {
                    if (it.entity.id == item.id) Item(item, isVisible = true, state = ItemState.Selected)
                    else Item(it.entity, isVisible = false, state = ItemState.Idle)
                }
            }
        )
    }
}
