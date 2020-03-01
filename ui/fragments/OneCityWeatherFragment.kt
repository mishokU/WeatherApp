package com.example.weatherapp.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.data.remote.models.CityProperty
import com.example.weatherapp.databinding.FragmentOneCityWeatherFragmentBinding
import com.example.weatherapp.domain.viewModelFactories.OneCityViewModelFactory
import com.example.weatherapp.domain.viewmodels.OneCityWeatherViewModel
import com.google.android.material.snackbar.Snackbar

class OneCityWeatherFragment : Fragment() {

    private lateinit var binding: FragmentOneCityWeatherFragmentBinding
    private lateinit var viewModel : OneCityWeatherViewModel
    private lateinit var cityProperty: CityProperty

    private var changeMenu = false

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOneCityWeatherFragmentBinding.inflate(inflater)

        cityProperty = OneCityWeatherFragmentArgs.fromBundle(arguments!!).cityProperty
        binding.cityWeather = cityProperty

        initToolbar()
        createViewModel()
        initObserver()

        return binding.root
    }

    private fun initObserver() {
        viewModel.rememberWeatherProperty.observe(viewLifecycleOwner, Observer {
            it?.let {
                viewModel.hasCurrentCity(it, cityProperty)
            }
        })

        viewModel.hasThisCity.observe(viewLifecycleOwner, Observer {
            it?.let {
                changeMenu = it
                onPrepareOptionsMenu(binding.oneCityToolbar.menu)
            }
        })
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        if(changeMenu) {
           menu.findItem(R.id.remember_button).isVisible = false
           menu.findItem(R.id.delete_city).isVisible = true
           viewModel.hasCurrentCityComplete()
        } else {
            menu.findItem(R.id.delete_city).isVisible = false
            menu.findItem(R.id.remember_button).isVisible = true
            viewModel.hasCurrentCityComplete()
        }
        super.onPrepareOptionsMenu(menu)
    }

    private fun initToolbar() {
        ((activity) as AppCompatActivity).setSupportActionBar(binding.oneCityToolbar)
        ((activity) as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.oneCityToolbar.setNavigationOnClickListener {
            this.findNavController().navigateUp()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.one_city_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.remember_button -> {
                viewModel.insert(cityProperty)
                showSnackBar("This city is added to list")
                item.isVisible = false
                return true
            }
            R.id.delete_city -> {
                viewModel.delete(cityProperty)
                showSnackBar("This city is deleted from list")
                item.isVisible = false
                return true
            }
            R.id.go_to_remembered ->{
                item.isVisible = false
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showSnackBar(text : String ) {
        Snackbar.make(this.requireView(), text, Snackbar.LENGTH_SHORT).show()
    }

    private fun createViewModel() {
        val application = requireNotNull(this.activity).application
        val viewModelFactory = OneCityViewModelFactory(cityProperty, application)
        viewModel = ViewModelProvider( this, viewModelFactory).get(OneCityWeatherViewModel::class.java)
    }
}
