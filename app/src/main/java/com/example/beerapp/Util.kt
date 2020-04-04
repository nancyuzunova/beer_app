package com.example.beerapp

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.api.load

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    if(imageUrl == null || imageUrl.isEmpty()) {
        view.load(R.drawable.beer_bottle)
    } else {
        view.load(imageUrl)
    }
}