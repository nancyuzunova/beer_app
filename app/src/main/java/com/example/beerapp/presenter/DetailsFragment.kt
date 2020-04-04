package com.example.beerapp.presenter

import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.beerapp.R
import com.example.beerapp.databinding.FragmentDetailsBinding
import com.example.domain.controller.DetailsViewModel
import com.google.android.material.snackbar.Snackbar

const val BEER_NOT_ADDED = R.string.beer_not_added
const val BEER_ADDED = R.string.beer_added
const val BEER_DELETED = R.string.beer_deleted

class DetailsFragment : BaseFragment() {

     val viewModel: DetailsViewModel by lazy {
        ViewModelProvider(this).get(DetailsViewModel::class.java)
    }

    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDetailsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        viewModel.id = args.beerId
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_to_favourites_menu, menu)
        val item = menu.findItem(R.id.add_to_favourites)
        viewModel.checkIfBeerIsFavourite(args.beerId) {
            setProperIcon(item)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_to_favourites -> {
                val id = args.beerId
                viewModel.checkIfBeerIsFavourite(id) {
                    if (it) {
                        deleteBeer(id, item)
                    } else {
                        saveBeer(id, item)
                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveBeer(id: Long, item: MenuItem) {
        viewModel.checkForSpace{
            if(it){
                viewModel.addBeerToFavourites(id)
                item.setIcon(R.drawable.favourite)
                Snackbar.make(this.view!!, BEER_ADDED, Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(ContextCompat.getColor(context!!, R.color.snackbar))
                    .setTextColor(Color.BLACK).show()
            }
            else {
                Snackbar.make(this.view!!, BEER_NOT_ADDED, Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(ContextCompat.getColor(context!!, R.color.snackbar))
                    .setTextColor(Color.BLACK).show()
            }
        }
    }

    private fun deleteBeer(id: Long, item: MenuItem) {
        viewModel.deleteBeerFromFavourites(id)
        item.setIcon(R.drawable.favourite_blank)
        Snackbar.make(this.view!!, BEER_DELETED, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(ContextCompat.getColor(context!!, R.color.snackbar))
            .setTextColor(Color.BLACK).show()
    }

    private fun setProperIcon(item: MenuItem) {
        if (viewModel.isBeerFavourite) {
            item.setIcon(R.drawable.favourite)
        } else {
            item.setIcon(R.drawable.favourite_blank)
        }
    }
}
