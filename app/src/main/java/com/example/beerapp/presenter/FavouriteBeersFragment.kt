package com.example.beerapp.presenter

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.GONE
import androidx.viewpager2.widget.ViewPager2.ORIENTATION_HORIZONTAL
import com.example.beerapp.NavigationDirections
import com.example.beerapp.R
import com.example.beerapp.adapter.BeerClickListener
import com.example.beerapp.adapter.FavouritesAdapter
import com.example.beerapp.databinding.FragmentFavouriteBeersBinding
import com.example.data.asDomainModel
import com.example.domain.controller.FavouritesViewModel
import com.google.android.material.tabs.TabLayoutMediator


class FavouriteBeersFragment : BaseFragment() {

    private val viewModel: FavouritesViewModel by lazy {
        ViewModelProvider(this).get(FavouritesViewModel::class.java)
    }

    private val adapter = FavouritesAdapter(BeerClickListener {beerId ->
        viewModel.onBeerClicked(beerId)
    })

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val binding = FragmentFavouriteBeersBinding.inflate(inflater)

        binding.viewModel = viewModel
        binding.viewPager2.adapter = adapter
        binding.viewPager2.orientation = ORIENTATION_HORIZONTAL

        with(binding.viewPager2){
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 3
        }

        transformCarouselView(binding.viewPager2)

        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->  
            binding.viewPager2.setCurrentItem(tab.position, true)
        }.attach()

        viewModel.favBeers.observe(viewLifecycleOwner, Observer {
            it.map { item ->
                item.asDomainModel()
            }
            if(it.isEmpty()){
                binding.viewPager2.visibility = View.GONE
                binding.tabLayout.visibility = View.GONE
                binding.noFavBeers.visibility = View.VISIBLE
            } else {
                binding.viewPager2.visibility = View.VISIBLE
                binding.tabLayout.visibility = View.VISIBLE
                binding.noFavBeers.visibility = View.GONE
            }
            adapter.submitList(it)
        })

        viewModel.navigateToBeerDetails.observe(viewLifecycleOwner, Observer {beerId ->
            beerId?.let {
                val mainNavController = Navigation.findNavController(this.activity!!, R.id.mainNavHostFragment)
                mainNavController.navigate(NavigationDirections.actionFavouriteBeersFragmentToDetailsFragment(it))
                viewModel.onBeerDetailsNavigated()
            }
        })
        return binding.root
    }

    private fun transformCarouselView(viewPager2: ViewPager2) {
        val pageMargin = resources.getDimensionPixelOffset(R.dimen.pageMargin)
        val offsetPx = resources.getDimensionPixelOffset(R.dimen.offset)

        viewPager2.setPageTransformer { page, position ->
            val viewPager = page.parent.parent as ViewPager2
            val offset = position * -(2 * offsetPx + pageMargin)
            if (viewPager.orientation == ORIENTATION_HORIZONTAL) {
                if (ViewCompat.getLayoutDirection(viewPager) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                    page.translationX = -offset
                } else {
                    page.translationX = offset
                }
            } else {
                page.translationY = offset
            }
        }
    }

}
