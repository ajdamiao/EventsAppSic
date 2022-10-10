package com.example.appevents.view

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.appevents.MainActivity
import com.example.appevents.R
import com.example.appevents.databinding.FragmentCheckInBinding
import com.example.appevents.exception.CustomException
import com.example.appevents.model.CheckIn
import com.example.appevents.util.Util
import com.example.appevents.viewmodel.CheckInViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.android.ext.android.inject

class CheckInFragment : Fragment(R.layout.fragment_check_in) {
    private lateinit var binding: FragmentCheckInBinding
    private val checkInViewModel: CheckInViewModel by viewModel()
    private lateinit var checkInDialog: Dialog
    private lateinit var checkInLoading: Dialog
    private val util by inject<Util>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCheckInBinding.bind(view)

        val main = activity as MainActivity
        main.setToolbarTitle(getString(R.string.text_toolbar_check_in))

        initComponents()
    }

    private fun initComponents() {
        checkInListener()

        val eventName = requireArguments().getString("eventName")
        val eventImage = requireArguments().getString("eventImage")
        val eventPrice = requireArguments().getString("eventPrice")
        val eventId = requireArguments().getString("id")

        binding.btnCheckIn.setOnClickListener {

            eventId?.let { id ->
                doCheckIn(id)
                util.hideKeyboard(requireContext(),requireView())
                checkInLoading()
            }
        }

        setupEventCardInfo(eventName.toString(), eventPrice.toString(), eventImage.toString())
    }

    private fun setupEventCardInfo(eventName: String, eventPrice: String, eventImage: String) {
        binding.checkInCard.txtCheckInEventName.text = eventName
        binding.checkInCard.txtCheckInEventPrice.text = String.format("%s", getString(R.string.txt_event_price), eventPrice)
        util.setImageGlide(requireView(), eventImage, binding.checkInCard.imgCheckInEvent)
    }

    private fun checkInListener() {
        checkInViewModel.checkInLiveData.observe(viewLifecycleOwner) { response ->
            when(response) {
                is Boolean -> {
                    checkInLoading.dismiss()
                    takeIf { response }
                        ?.run { checkInDialog(resources.getString(R.string.success), resources.getString(R.string.success_checkin)) }
                        ?: checkInDialog(resources.getString(R.string.error), resources.getString(R.string.error_checkin))
                }
                else -> {
                    checkInLoading.dismiss()
                    checkInDialog(resources.getString(R.string.error), resources.getString(R.string.error_checkin))
                }
            }
        }
    }

    private fun checkInLoading() {
        checkInLoading = Dialog(requireActivity()).apply {
            window
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
            setContentView(R.layout.check_in_dialog_custom_layout)
            show()
        }
    }

    private fun checkInDialog(title: String, message: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(resources.getString(R.string.accept)) { _, _ ->
                Navigation.findNavController(requireView()).popBackStack()
            }
            .show()
    }

    private fun inputErrorDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.error))
            .setMessage(resources.getString(R.string.invalid_name_email))
            .setPositiveButton(resources.getString(R.string.accept)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun doCheckIn(eventID: String) {
        val email = binding.inputEmail.text.toString()
        val name = binding.inputName.text.toString()

        if(util.isEmailValid(email) && util.isNameValid(name)) {
            checkInViewModel.doCheckIn(CheckIn(eventID, name, email))
        }
        else {
            inputErrorDialog()
        }
    }
}