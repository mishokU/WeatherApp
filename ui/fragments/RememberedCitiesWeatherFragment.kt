package com.example.weatherapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.databinding.FragmentRememberedCitiesBinding
import com.example.weatherapp.domain.viewModelFactories.RememberedViewModelFactory
import com.example.weatherapp.domain.viewmodels.RememberedCityViewModel
import com.example.weatherapp.ui.adapters.AllCitiesRecyclerViewAdapter

class RememberedCitiesWeatherFragment : Fragment() {

    private lateinit var binding: FragmentRememberedCitiesBinding
    private lateinit var viewModel: RememberedCityViewModel
    private lateinit var rememberAdapter : AllCitiesRecyclerViewAdapter
    private var refresh : Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRememberedCitiesBinding.inflate(inflater)

        initViewModel()
        initRecyclerView()
        initObservers()

        return binding.root
    }


    private fun initRecyclerView() {
        rememberAdapter = AllCitiesRecyclerViewAdapter(AllCitiesRecyclerViewAdapter.CityAdapterListener{
            viewModel.displayCityWithWeather(it)
        })
        binding.rememberedRecyclerView.adapter = rememberAdapter
    }

    private fun initObservers() {
        viewModel.rememberWeatherProperty.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it.isNotEmpty()){
                    binding.emptyText.visibility = View.INVISIBLE
                }
                //Refresh data once
                if(refresh) {
                   viewModel.refreshAll(it)
                   refresh = false
                }

                rememberAdapter.submitList(it)
            }
        })
        viewModel.showFullCityWeather.observe(viewLifecycleOwner, Observer {
            if(null != it){
                this.findNavController().navigate(RememberedCitiesWeatherFragmentDirections.
                    actionRememberedCityWeatherFragmentToOneCityWeatherFramgnet(it))
                viewModel.displayPropertyDetailsComplete()
            }
        })

        binding.deleteAllButton.setOnClickListener {
            viewModel.deleteAll()
        }
    }


    private fun initViewModel() {
        val application = requireNotNull(this.activity).application
        val rememberedViewModelFactory = RememberedViewModelFactory(application)
        viewModel = ViewModelProvider(this,rememberedViewModelFactory).get(RememberedCityViewModel::class.java)

        binding.lifecycleOwner = this
    }

}
