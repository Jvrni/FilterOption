package com.jvrni.filteroption.adapter

import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.jvrni.filteroption.R
import com.jvrni.filteroption.databinding.ItemCloseBinding
import com.jvrni.filteroption.databinding.ItemFilterBinding
import com.jvrni.filteroption.models.Item
import com.jvrni.filteroption.models.FilterEntity
import com.jvrni.filteroption.models.ItemState
import kotlinx.coroutines.flow.MutableStateFlow


class FilterOptionAdapter(
    private val list: MutableStateFlow<List<Item>>,
    private val borderColor: Int,
    private val borderColorSelected: Int,
    private val backgroundColorItem: Int,
    private val backgroundColorItemSelected: Int,
    private val textColor: Int,
    private val textColorSelected: Int,
    private val onSelectFilter: (FilterEntity) -> Unit,
    private val onClearFilter: () -> Unit,
) : RecyclerView.Adapter<ViewHolder>() {

    class CloseHolder(private val binding: ItemCloseBinding) : ViewHolder(binding.root) {
        fun bind(
            list: List<Item>,
            borderColor: Int,
            backgroundColorItem: Int,
            textColor: Int,
            onClose: () -> Unit
        ) {
            binding.card.strokeColor = borderColor
            binding.card.setCardBackgroundColor(backgroundColorItem)
            binding.icon.setColorFilter(textColor)

            if (list.firstOrNull { it.state == ItemState.Selected } != null) {
                val animSlideIn =
                    AnimationUtils.loadAnimation(binding.root.context, R.anim.slide_in)
                binding.root.startAnimation(animSlideIn)
                binding.root.visibility = View.VISIBLE
                binding.card.visibility = View.VISIBLE
            } else {
                val animSlideOut =
                    AnimationUtils.loadAnimation(binding.root.context, R.anim.slide_out)
                binding.root.visibility = View.GONE
                binding.card.visibility = View.GONE
                binding.root.startAnimation(animSlideOut)
            }

            binding.card.setOnClickListener { onClose.invoke() }
        }
    }

    class FilterHolder(private val binding: ItemFilterBinding) : ViewHolder(binding.root) {
        fun bind(
            item: Item,
            borderColor: Int,
            borderColorSelected: Int,
            backgroundColorItem: Int,
            backgroundColorItemSelected: Int,
            textColor: Int,
            textColorSelected: Int,
            onAction: (FilterEntity) -> Unit
        ) {
            val isSelected = item.state == ItemState.Selected

            binding.card.strokeColor = if (isSelected) borderColorSelected else borderColor
            binding.card.setCardBackgroundColor(if (isSelected) backgroundColorItemSelected else backgroundColorItem)
            binding.value.setTextColor(if (isSelected) textColorSelected else textColor)

            if (item.isVisible) {
                val animSlideIn =
                    AnimationUtils.loadAnimation(binding.root.context, R.anim.slide_in)
                binding.root.startAnimation(animSlideIn)
                binding.card.visibility = View.VISIBLE

                Handler().postDelayed({
                    binding.root.visibility = View.VISIBLE
                }, 700)
            } else {
                val animSlideOut =
                    AnimationUtils.loadAnimation(binding.root.context, R.anim.slide_out)
                binding.root.visibility = View.GONE
                binding.card.visibility = View.GONE
                binding.root.startAnimation(animSlideOut)
            }

            binding.value.text = item.entity.value
            binding.card.setOnClickListener { onAction.invoke(item.entity) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        when (viewType) {
            CLOSE_VIEW -> {
                val binding =
                    ItemCloseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                CloseHolder(binding)
            }
            else -> {
                val binding =
                    ItemFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                FilterHolder(binding)
            }
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is CloseHolder -> holder.bind(
                list.value,
                borderColor,
                backgroundColorItem,
                textColor
            ) {
                onClearFilter.invoke()
                notifyDataSetChanged()
            }
            is FilterHolder -> holder.bind(
                list.value[position.minus(1)],
                borderColor,
                borderColorSelected,
                backgroundColorItem,
                backgroundColorItemSelected,
                textColor,
                textColorSelected,
            ) {
                if (list.value.firstOrNull { it.state == ItemState.Selected } != null) return@bind

                onSelectFilter.invoke(it)
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int = list.value.size.plus(1)

    override fun getItemViewType(position: Int): Int {
        if (position == 0) return CLOSE_VIEW
        return super.getItemViewType(position.minus(1))
    }

    companion object {
        private const val CLOSE_VIEW = 999
    }
}
