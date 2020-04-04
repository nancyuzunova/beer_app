package com.example.beerapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.get
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.beerapp.databinding.FavouriteBeerItemBinding
import com.example.beerapp.loadImage
import com.example.data.asDomainModel
import com.example.data.database.FavouriteEntity
import com.example.data.model.Beer

class FavouritesAdapter (val clickListener: BeerClickListener) : ListAdapter<FavouriteEntity, FavouritesAdapter.FavouritesViewHolder>(FavouritesDiffCallback){

    companion object {
        val FavouritesDiffCallback = object : DiffUtil.ItemCallback<FavouriteEntity>() {
            override fun areItemsTheSame(oldItem: FavouriteEntity, newItem: FavouriteEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: FavouriteEntity, newItem: FavouriteEntity): Boolean {
                return oldItem == newItem
            }

        }
    }

    class FavouritesViewHolder private constructor(val binding: FavouriteBeerItemBinding)
        : RecyclerView.ViewHolder(binding.root){

        companion object{
            fun from(parent: ViewGroup) : FavouritesViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FavouriteBeerItemBinding.inflate(layoutInflater, parent, false)
                return FavouritesViewHolder(binding)
            }
        }

        fun bind(item: FavouriteEntity?, clickListener: BeerClickListener){
            loadImage(binding.favBeerImage, item?.imageUrl)
            binding.favBeerName.text = item?.name
            binding.favBeerTagline.text = item?.tagline
            val desc = binding.favBeerDescription[0] as TextView
            desc.text = item?.description
            binding.favBeerAbvValue.text = item?.alcoholByVolume.toString()
            binding.favBeerIbuValue.text = item?.internationalBitternessUnits.toString()
            binding.favBeerEbcValue.text = item?.europeanBeerConventionColor.toString()
            binding.clickListener = clickListener
            binding.beer = item?.asDomainModel()
            //TODO add food pairings
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesViewHolder {
        return FavouritesViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: FavouritesViewHolder, position: Int) {
        return holder.bind(getItem(position), clickListener)
    }
}