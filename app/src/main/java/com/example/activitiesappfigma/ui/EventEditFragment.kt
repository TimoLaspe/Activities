package com.example.activitiesappfigma.ui

import android.app.Activity.RESULT_OK
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.activitiesappfigma.MainActivity
import com.example.activitiesappfigma.MainViewModel
import com.example.activitiesappfigma.R
import com.example.activitiesappfigma.data.model.Event
import com.example.activitiesappfigma.databinding.FragmentEventeditBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.text.SimpleDateFormat
import java.util.*


class EventEditFragment : Fragment() {

    lateinit var binding: FragmentEventeditBinding
    private val viewModel: MainViewModel by activityViewModels()

    private var uri: Uri? = null
    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { localUri: Uri? ->
        if (localUri != null) {
            uri = localUri
            binding.eventeditImage.load(uri)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEventeditBinding.inflate(inflater)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.lifecycleScope?.launchWhenCreated {
            (activity as MainActivity).showBottomNav()
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editImageButton.setOnClickListener {
            getContent.launch("image/*")
        }


        binding.categorySpinner.setOnTouchListener { view, motionEvent ->
            val imm: InputMethodManager =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }


        binding.btnCalendar.setOnClickListener {
            // create new instance of DatePickerFragment
            val datePickerFragment = DatePicker()
            val supportFragmentManager = requireActivity().supportFragmentManager

            //  implement setFragmentResultListener
            supportFragmentManager.setFragmentResultListener(
                "REQUEST_KEY",
                viewLifecycleOwner
            ) { resultKey, bundle ->
                if (resultKey == "REQUEST_KEY") {
                    val date = bundle.getString("SELECTED_DATE")
                    binding.dateTextTv.text = date
                }
            }

            datePickerFragment.show(supportFragmentManager, "DatePickerFragment")
        }


        binding.eventeditStartEventButton.setOnClickListener {
            startEvent()

            binding.eventeditTextInputName.text = null
            binding.eventeditTextInputInfo.text = null
            binding.dateTextTv.text = null
            binding.eventeditTextInputLocation.text = null
            findNavController().navigate(R.id.eventListFragment)
        }

    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun startEvent() {
        val eventEditName = binding.eventeditTextInputName.text.toString()
        val eventEditInfo = binding.eventeditTextInputInfo.text.toString()
        val eventEditDateTime = binding.dateTextTv.text.toString()
        val eventEditLocation = binding.eventeditTextInputLocation.text.toString()
        val eventEditCategory = binding.categorySpinner.selectedItem.toString()
        val eventEditRoutine = binding.routineSwitchButton

        var isRoutine = false

        eventEditRoutine.setOnCheckedChangeListener { compoundButton, onswitch ->
            if (onswitch) isRoutine = true
            else isRoutine = false
        }

        if (!eventEditName.isNullOrEmpty() && !eventEditInfo.isNullOrEmpty() && !eventEditDateTime
                .isNullOrEmpty() && !eventEditLocation.isNullOrEmpty()
        ) {
            val newEvent = Event(
                image = "",
                name = eventEditName,
                info = eventEditInfo,
                dateAndTime = eventEditDateTime,
                location = eventEditLocation,
                category = eventEditCategory,
                routine = isRoutine
            )
            viewModel.insertEvent(newEvent, uri)

        }
    }


}