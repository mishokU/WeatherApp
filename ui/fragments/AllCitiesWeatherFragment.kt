package com.example.weatherapp.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentAllCitiesWeatherBinding
import com.example.weatherapp.domain.utils.ConnectionStatus
import com.example.weatherapp.domain.viewModelFactories.AllCitiesViewModelFactory
import com.example.weatherapp.domain.viewmodels.AllCitiesWeatherViewModel
import com.example.weatherapp.ui.adapters.AllCitiesRecyclerViewAdapter
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class AllCitiesWeatherFragment : Fragment() {

    private lateinit var binding: FragmentAllCitiesWeatherBinding
    private lateinit var viewModel: AllCitiesWeatherViewModel
    private lateinit var compositeDisposable: CompositeDisposable
    private lateinit var navigation : NavController
    private lateinit var adapter: AllCitiesRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAllCitiesWeatherBinding.inflate(inflater)
        compositeDisposable = CompositeDisposable()
        navigation = Navigation.findNavController(this.activity as AppCompatActivity,R.id.myNavHostFragment)
        (activity as AppCompatActivity).setSupportActionBar(binding.mainToolbar)

        initViewModel()
        initSearchTextWatcher()
        initObservables()
        initRecyclerView()

        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.go_to_remembered -> {
                navigation.navigate(R.id.rememberedCityWeatherFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun initRecyclerView() {
        adapter = AllCitiesRecyclerViewAdapter(AllCitiesRecyclerViewAdapter.CityAdapterListener {
            viewModel.displayCityWithWeather(it)
        })
        binding.recyclerView.adapter = adapter
    }

    private fun initObservables() {
        viewModel.networkStatus.observe(viewLifecycleOwner, Observer {
            it?.let {
                when(it){
                    ConnectionStatus.LOADING -> {
                        binding.testText.text = it.toString()
                        binding.testText.visibility = View.VISIBLE
                    }
                    ConnectionStatus.ERROR -> {
                        binding.testText.text = it.toString()
                        binding.testText.visibility = View.VISIBLE
                    }
                    ConnectionStatus.DONE -> {
                        binding.testText.text = it.toString()
                    }
                }
            }
        })

        viewModel.showFullCityWeather.observe(viewLifecycleOwner, Observer {
            if(null != it){
                this.findNavController().navigate(AllCitiesWeatherFragmentDirections.actionAllCitiesWeatherFragmentToOneCityWeatherFramgnet(it))
                viewModel.displayPropertyDetailsComplete()
            }
        })
        viewModel.weatherCitiesProperty.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.replaceList(it)
            }
        })

    }

    private fun initViewModel() {
        val application = requireNotNull(this.activity).application
        val searchViewModelFactory = AllCitiesViewModelFactory(application)
        viewModel = ViewModelProvider(this,searchViewModelFactory).get(AllCitiesWeatherViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    private fun initSearchTextWatcher() {
        compositeDisposable.add(
            RxTextView.textChanges(binding.searchText)
                .skipInitialValue()
                .debounce(1, TimeUnit.SECONDS)
                .map(CharSequence::toString)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .doOnNext {
                    viewModel.setSearchedCityName(it)
                }
                .subscribe()
        )
    }

    override fun onDestroyView() {
        compositeDisposable.dispose()
        super.onDestroyView()
    }
}
