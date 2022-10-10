package com.example.appevents.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.appevents.MainActivity
import com.example.appevents.R
import com.example.appevents.databinding.FragmentEventDetailsBinding
import com.example.appevents.model.Event
import com.example.appevents.util.Util
import com.example.appevents.viewmodel.EventDetailsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EventDetailsFragment : Fragment(R.layout.fragment_event_details) {
    private lateinit var binding: FragmentEventDetailsBinding
    private val eventDetailsViewModel: EventDetailsViewModel by viewModel()
    private var eventAddress = String()
    private var eventName = String()
    private var eventPrice = String()
    private var eventID = String()
    private var eventImage = String()
    private val util = Util()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEventDetailsBinding.bind(view)

        val main = activity as MainActivity
        main.setToolbarTitle(getString(R.string.text_toolbar_event_list))

        initComponents()
    }

    private fun initComponents() {
        val id = requireArguments().getString("id")

        id?.let { eventDetailsViewModel.getEventDetails(it) }
        observerEventDetails()

        binding.run {
            btnShowEventDescription.setOnClickListener {
                showMoreOrLessDescription()
            }

            btnShare.setOnClickListener {
                util.shareEvent(eventAddress,eventPrice,requireContext())
            }

            btnCheckIn.setOnClickListener {
                val directions = EventDetailsFragmentDirections.actionEventDetailsFragmentToCheckInFragment(eventName,eventPrice,eventID, eventImage)
                findNavController().navigate(directions)
            }
        }
    }

    private fun observerEventDetails() {
        eventDetailsViewModel.eventDetailsLiveData.observe(viewLifecycleOwner) {
            when(it) {
                is Event -> {
                    setupEventInfo(it)
                }

                else -> { showBlankState() }
            }
        }
    }

    private fun showContent() {
        binding.run {
            progressBar.visibility = View.GONE
            eventContent.visibility = View.VISIBLE
            containerActions.visibility = View.VISIBLE
        }
    }

    private fun setupEventInfo(response: Event) {
        eventAddress = util.getEventLocation(response.latitude, response.longitude, requireContext())
        eventPrice = response.price
        eventName = response.title
        eventID = response.id
        eventImage = response.image

        binding.run {
            txtEventTitle.text = eventName
            txtEventDescription.text = response.description
            txtEventPrice.text = getString(R.string.txt_event_price, eventPrice)
            txtEventDate.text = util.dateFormatter(response.date)
            txtEventLocation.text = eventAddress
        }

        util.setImageGlide(requireView(), eventImage, binding.imgEvent)

        showContent()
    }

    private fun showBlankState() {
        binding.run {
            progressBar.visibility = View.GONE
            eventContent.visibility = View.GONE
            containerActions.visibility = View.GONE
            txtBlankState.visibility = View.VISIBLE
        }
    }

    private fun showMoreOrLessDescription() {
        val btnShowMore = binding.btnShowEventDescription

        btnShowMore.setOnClickListener {
            if (btnShowMore.text.equals(getString(R.string.more_event_details))) {
                binding.txtEventDescription.maxLines = Int.MAX_VALUE
                btnShowMore.text = getString(R.string.less_event_details)
            } else {
                binding.txtEventDescription.maxLines = 3
                btnShowMore.text = getString(R.string.more_event_details)
            }
        }
    }
}