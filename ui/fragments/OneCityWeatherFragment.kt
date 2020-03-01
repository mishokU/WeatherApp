package com.example.weatherapp.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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

        return binding.root
    }

    private fun initToolbar() {
        ((activity) as AppCompatActivity).setSupportActionBar(binding.oneCityToolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.one_city_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.remember_button -> {
                viewModel.insert(cityProperty)
                showSnackBar()
            }
            R.id.go_to_remembered -> {
                item.isVisible = false
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showSnackBar() {
        Snackbar.make(this.requireView(),"This city is added to list", Snackbar.LENGTH_SHORT).show()
    }

    private fun createViewModel() {
        val application = requireNotNull(this.activity).application
        val viewModelFactory = OneCityViewModelFactory(cityProperty, application)
        viewModel = ViewModelProvider( this, viewModelFactory).get(OneCityWeatherViewModel::class.java)
    }
}
