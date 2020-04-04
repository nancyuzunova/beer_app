package com.example.beerapp.presenter

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.beerapp.NavigationDirections
import com.example.beerapp.R
import com.example.beerapp.adapter.BeerClickListener

import com.example.beerapp.adapter.BeerListAdapter
import com.example.beerapp.databinding.FragmentHomeBinding
import com.example.data.asDomainModel
import com.example.domain.controller.HomeViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

private const val DEBOUNCE_TIMEOUT: Long = 300
const val NO_FAV_BEER = R.string.no_fav_beer

class HomeFragment : BaseFragment(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Main

    private val adapter = BeerListAdapter(BeerClickListener {beerId ->
        viewModel.onBeerClicked(beerId)
    })

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    private var nameEditText: EditText? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentHomeBinding.inflate(inflater)
        binding.viewModel = viewModel

        binding.beerList.adapter = adapter
        observe(binding)

        viewModel.navigateToBeerDetails.observe(viewLifecycleOwner, Observer {beerId ->
            beerId?.let {
                val mainNavController = Navigation.findNavController(this.activity!!, R.id.mainNavHostFragment)
                mainNavController.navigate(NavigationDirections.actionHomeFragmentToDetailsFragment(it))
                viewModel.onBeerDetailsNavigated()
            }
        })

        binding.beerList.scrollToPosition(0)

        nameEditText = binding.searchGroup[0] as EditText
        nameEditText?.addTextChangedListener(object: TextWatcher{
            private var searchFor = ""

            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText = s.toString().trim()
                if(searchText == searchFor){
                    return
                }
                searchFor = searchText
                launch {
                    delay(DEBOUNCE_TIMEOUT)
                    if(searchFor != searchText){
                        return@launch
                    }
                    viewModel.filterBeers(searchFor)
                }
                binding.beerList.scrollToPosition(0)
            }
        })
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        nameEditText?.setText(viewModel.nameFilter)
        launch {
            viewModel.filterBeers(viewModel.nameFilter)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.filters_menu, menu)
        inflater.inflate(R.menu.random_beer, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.filtersFragment -> {
                Navigation.findNavController(this.activity!!, R.id.mainNavHostFragment).navigate(item.itemId)
                true
            }
            R.id.get_random_beer -> {
                launch {
                    if(viewModel.isRandomFromFavourites()){
                        if(viewModel.hasFavourites()){
                            val randomBeer = viewModel.getRandomFromFavourites()
                            Navigation.findNavController(activity!!, R.id.mainNavHostFragment).navigate(
                                NavigationDirections.actionHomeFragmentToDetailsFragment(randomBeer!!.id))
                        } else {
                            Snackbar.make(view!!, NO_FAV_BEER, Snackbar.LENGTH_SHORT)
                                .setBackgroundTint(ContextCompat.getColor(context!!, R.color.snackbar))
                                .setTextColor(Color.BLACK).show()
                        }
                    }
                    else{
                        val randomBeer = viewModel.getRandomBeer()
                        Navigation.findNavController(activity!!, R.id.mainNavHostFragment).navigate(
                            NavigationDirections.actionHomeFragmentToDetailsFragment(randomBeer.id))
                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun observe(binding: FragmentHomeBinding) {
        viewModel.allBeers.removeObservers(viewLifecycleOwner)
        viewModel.allBeers.observe(viewLifecycleOwner, Observer {
            it.map { e ->
                e?.asDomainModel()
            }
            if(it.isEmpty()){
                binding.beerList.visibility = View.GONE
                binding.noBeers.visibility = View.VISIBLE
            } else {
                binding.beerList.visibility = View.VISIBLE
                binding.noBeers.visibility = View.GONE
            }
            adapter.submitList(it)
        })
    }
}
