package com.example.appevents.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appevents.R
import com.example.appevents.adapter.EventsAdapter
import com.example.appevents.databinding.FragmentHomeBinding
import com.example.appevents.model.Event
import com.example.appevents.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        (activity as AppCompatActivity).supportActionBar?.show()

        initComponents()
    }

    private fun initComponents() {
        observerEvent()
        homeViewModel.getEvent()
    }

    private fun observerEvent() {
        homeViewModel.eventListLiveData.observe(viewLifecycleOwner) {
            when(it){
                is ArrayList<Event> -> setupCard(it)

                else -> { showBlankState() }
            }
        }
    }

    private fun showBlankState() {
        binding.run {
            rviewEventsCard.visibility = View.GONE
            progressBar2.visibility = View.GONE
            txtBlankState.visibility = View.VISIBLE
        }
    }

    private fun setupCard(events: ArrayList<com.example.appevents.model.Event>) {
        binding.run {
            progressBar2.visibility = View.GONE
            rviewEventsCard.visibility = View.VISIBLE
            rviewEventsCard.layoutManager = LinearLayoutManager(requireContext())
            rviewEventsCard.adapter = EventsAdapter(events, requireContext())
        }
    }
}