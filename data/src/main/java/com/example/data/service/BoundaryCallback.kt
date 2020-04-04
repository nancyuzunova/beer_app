package com.example.data.service

import androidx.paging.PagedList
import com.example.data.database.DatabaseBeer
import com.example.data.model.Filter
import com.example.data.repository.BoundaryCallbackListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
const val MAX_NUMBER_OF_PAGES = 1000
class BoundaryCallback(private val listener: BoundaryCallbackListener, private val scope: CoroutineScope)
    : PagedList.BoundaryCallback<DatabaseBeer>(){

    var page: Int = 1
    var filter: Filter = Filter()
    var totalPages: Int = page

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        scope.launch {
            listener.loadPage(page, filter)
        }
    }

    override fun onItemAtEndLoaded(itemAtEnd: DatabaseBeer) {
        super.onItemAtEndLoaded(itemAtEnd)
        if(page < totalPages){
            page++
            scope.launch {
                listener.loadPage(page, filter)
            }
        }
    }
}