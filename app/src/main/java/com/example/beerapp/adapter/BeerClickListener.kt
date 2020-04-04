package com.example.beerapp.adapter

import com.example.data.model.Beer

class BeerClickListener(val clickListener: (beerId: Long) -> Unit){

    fun onClick(beer: Beer) = clickListener(beer.id)
}