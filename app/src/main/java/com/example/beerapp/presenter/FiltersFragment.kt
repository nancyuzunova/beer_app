package com.example.beerapp.presenter

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.beerapp.R
import com.example.beerapp.databinding.FragmentFiltersBinding
import com.example.data.model.Filter
import com.example.domain.controller.FiltersViewModel
import java.text.SimpleDateFormat
import java.util.*

class FiltersFragment : BaseFragment(){

    private var yeast: EditText? = null
    private var hops: EditText? = null
    private var malt: EditText? = null
    private var food: EditText? = null
    private var ibuFrom: EditText? = null
    private var ibuTo: EditText? = null
    private var abvFrom: EditText? = null
    private var abvTo: EditText? = null
    private var ebcFrom: EditText? = null
    private var ebcTo: EditText? = null
    private var after: TextView? = null
    private var before: TextView? = null

    private val calendar = Calendar.getInstance()

    private val viewModel by lazy {
        ViewModelProvider(this).get(FiltersViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFiltersBinding.inflate(inflater)
        binding.viewModel = viewModel

        initializeEditTexts(binding)
        binding.clearYeast.setOnClickListener {
            yeast?.setText("")
        }
        binding.clearHops.setOnClickListener {
            hops?.setText("")
        }
        binding.clearABV.setOnClickListener {
            abvFrom?.setText("")
            abvTo?.setText("")
        }
        binding.clearAfter.setOnClickListener {
            after?.setText("")
        }
        binding.clearBefore.setOnClickListener {
            before?.setText("")
        }
        binding.clearEBC.setOnClickListener {
            ebcFrom?.setText("")
            ebcTo?.setText("")
        }
        binding.clearFood.setOnClickListener {
            food?.setText("")
        }
        binding.clearIBU.setOnClickListener {
            ibuFrom?.setText("")
            ibuTo?.setText("")
        }
        binding.clearMalt.setOnClickListener {
            malt?.setText("")
        }
        val dateAfterSetListener =
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInAfterTextView()
            }
        binding.afterValue.setOnClickListener {
            DatePickerDialog(context!!, dateAfterSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
        val dateBeforeSetListener =
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInBeforeTextView()
            }
        binding.beforeValue.setOnClickListener {
            DatePickerDialog(context!!, dateBeforeSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        saveFiltersAfterPause()
    }

    override fun onPause() {
        val filter = createFilter()
        viewModel.saveFilters(filter)
        super.onPause()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.clear_filters_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.clear_filters -> {
                viewModel.clearFilters()
                clearEditTexts()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveFiltersAfterPause() {
        val filter = viewModel.getFilter()
        yeast?.setText(filter.yeast)
        hops?.setText(filter.hops)
        malt?.setText(filter.malt)
        food?.setText(filter.food)
        ibuFrom?.setText(when(filter.ibuFrom){
            0.0 -> ""
            else -> filter.ibuFrom.toString()
        })
        ibuTo?.setText(when(filter.ibuTo){
            0.0 -> ""
            else -> filter.ibuTo.toString()
        })
        abvFrom?.setText(when(filter.abvFrom){
            0.0 -> ""
            else -> filter.abvFrom.toString()
        })
        abvTo?.setText(when(filter.abvTo){
            0.0 -> ""
            else -> filter.abvTo.toString()
        })
        ebcFrom?.setText(when(filter.ebcFrom){
            0.0 -> ""
            else -> filter.ebcFrom.toString()
        })
        ebcTo?.setText(when(filter.ebcTo){
            0.0 -> ""
            else -> filter.ebcTo.toString()
        })
        after?.setText(filter.after)
        before?.setText(filter.before)
    }

    private fun createFilter(): Filter {
        return Filter(
            yeast = yeast?.text.toString(), hops = hops?.text.toString(),
            malt = malt?.text.toString(), food = food?.text.toString(),
            ibuFrom = when {
                ibuFrom?.text.toString().isEmpty() -> 0.0
                else -> ibuFrom?.text.toString().toDouble()
            },
            ibuTo = when {
                ibuTo?.text.toString().isEmpty() -> 0.0
                else -> ibuTo?.text.toString().toDouble()
            },
            abvFrom = when {
                abvFrom?.text.toString().isEmpty() -> 0.0
                else -> abvFrom?.text.toString().toDouble()
            },
            abvTo = when {
                abvTo?.text.toString().isEmpty() -> 0.0
                else -> abvTo?.text.toString().toDouble()
            },
            ebcFrom = when {
                ebcFrom?.text.toString().isEmpty() -> 0.0
                else -> ebcFrom?.text.toString().toDouble()
            },
            ebcTo = when {
                ebcTo?.text.toString().isEmpty() -> 0.0
                else -> ebcTo?.text.toString().toDouble()
            },
            after = after?.text.toString(), before = before?.text.toString()
        )
    }

    private fun clearEditTexts() {
        yeast?.setText("")
        hops?.setText("")
        malt?.setText("")
        food?.setText("")
        ibuFrom?.setText("")
        ibuTo?.setText("")
        abvFrom?.setText("")
        abvTo?.setText("")
        ebcTo?.setText("")
        ebcFrom?.setText("")
        after?.setText("")
        before?.setText("")
    }

    private fun initializeEditTexts(binding: FragmentFiltersBinding) {
        yeast = binding.yeastEditText
        hops = binding.hopsEditText
        malt = binding.maltEditText
        food = binding.foodEditText
        ibuFrom = binding.ibuFromValue
        ibuTo = binding.ibuToValue
        abvFrom = binding.abvFromValue
        abvTo = binding.abvToValue
        ebcFrom = binding.ebcFromValue
        ebcTo = binding.ebcToValue
        after = binding.afterValue
        before = binding.beforeValue
    }

    private fun updateDateInBeforeTextView() {
        val format = "MM/yyyy"
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        before?.text = sdf.format(calendar.time)
    }

    private fun updateDateInAfterTextView() {
        val format = "MM/yyyy"
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        after?.text = sdf.format(calendar.time)
    }
}