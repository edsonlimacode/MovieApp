package com.edsonlima.flixapp.presenter.main.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.edsonlima.flixapp.R
import com.edsonlima.flixapp.databinding.ItemProfileOptionsBinding
import com.edsonlima.flixapp.domain.model.ItemMenuProfile
import com.edsonlima.flixapp.domain.model.Type

class MenuProfileAdapter(
    private val items: List<ItemMenuProfile>,
    val onClickListener: (Type) -> Unit
) : Adapter<MenuProfileAdapter.MenuProfileViewHolder>() {

    inner class MenuProfileViewHolder(
        val binding: ItemProfileOptionsBinding
    ) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuProfileViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        val view = ItemProfileOptionsBinding.inflate(inflater, parent, false)

        return MenuProfileViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MenuProfileViewHolder, position: Int) {
        val item = items[position]
        with(holder) {
            binding.imgMenuProfile.setImageDrawable(
                ContextCompat.getDrawable(
                    binding.root.context,
                    item.icon
                )
            )

            binding.textMenuProfile.text = item.text

            holder.itemView.setOnClickListener {
                onClickListener(item.type)
            }
        }
    }

}