package com.edsonlima.flixapp.presenter.main.movie.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.edsonlima.flixapp.R
import com.edsonlima.flixapp.databinding.ItemCreditBinding
import com.edsonlima.flixapp.domain.model.Person

class MovieCreditAdapter() :
    ListAdapter<Person, MovieCreditAdapter.CreditViewHolder>(itemCallback) {

    companion object {
        val itemCallback = object : DiffUtil.ItemCallback<Person>() {
            override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class CreditViewHolder(
        val binding: ItemCreditBinding
    ) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreditViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        val view = ItemCreditBinding.inflate(inflater, parent, false)

        return CreditViewHolder(view)
    }

    override fun onBindViewHolder(holder: CreditViewHolder, position: Int) {
        val person = getItem(position)

        with(holder) {

            if(person.profilePath?.isNotEmpty() == true){
                Glide.with(holder.binding.root.context)
                    .load("https://image.tmdb.org/t/p/w500${person.profilePath}")
                    .into(holder.binding.imgPerson)
            }else {
                holder.binding.imgPerson.setImageResource(R.drawable.image_default)
            }

            binding.textPersonName.text = person.name
            binding.textDepartament.text = person.knownForDepartment

        }
    }
}