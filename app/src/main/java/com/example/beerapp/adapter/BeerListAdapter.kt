package com.example.beerapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.beerapp.R
import com.example.beerapp.databinding.ListItemBeerBinding
import com.example.data.asDomainModel
import com.example.data.database.DatabaseBeer
import com.example.data.model.Beer

class BeerListAdapter(val clickListener: BeerClickListener): PagedListAdapter<DatabaseBeer, BeerListAdapter.ViewHolder>(BeerDiffCallback){

    companion object {
        val BeerDiffCallback = object : DiffUtil.ItemCallback<DatabaseBeer>() {
            override fun areItemsTheSame(oldItem: DatabaseBeer, newItem: DatabaseBeer): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DatabaseBeer, newItem: DatabaseBeer): Boolean {
                return oldItem == newItem
            }
        }
    }

    class ViewHolder private constructor(val binding: ListItemBeerBinding): RecyclerView.ViewHolder(binding.root){

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemBeerBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(item: DatabaseBeer?, clickListener: BeerClickListener) {
            binding.beerName.text = item?.name
            binding.beerTagline.text = item?.tagline
            loadBeerImage(item?.imageUrl)
            binding.beer = item?.asDomainModel()
            binding.clickListener = clickListener
        }

        private fun loadBeerImage(imageUrl: String?) {
            if(imageUrl == null || imageUrl.isEmpty()){
                binding.beerImage.load(R.drawable.beer_bottle)
            }
            else{
                binding.beerImage.load(imageUrl)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        return holder.bind(item, clickListener)
    }
}