package com.example.activitiesappfigma.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.activitiesappfigma.MainActivity
import com.example.activitiesappfigma.MainViewModel
import com.example.activitiesappfigma.R
import com.example.activitiesappfigma.adapter.EventAdapter
import com.example.activitiesappfigma.adapter.PhotoAdapter
import com.example.activitiesappfigma.data.model.Event
import com.example.activitiesappfigma.data.model.Photo
import com.example.activitiesappfigma.databinding.FragmentEventdetailBinding


class EventDetailFragment: Fragment(){

    lateinit var binding: FragmentEventdetailBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEventdetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.lifecycleScope?.launchWhenCreated {
            (activity as MainActivity).showBottomNav()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val photoAdapter = PhotoAdapter()
        binding.photoRecycler.adapter = photoAdapter

/*
        val list : MutableList<Photo>

        viewModel.event.observe(
            viewLifecycleOwner,
            Observer {
                photoAdapter.submitList(list)
            }
        )
*/
        viewModel.getEventData()

        viewModel.event.observe(
            viewLifecycleOwner,
            Observer {
                    binding.detailImage.setImageResource(R.drawable.app_logo)
                    binding.detailEventnameText.text = it.name
                    binding.detailDescriptionText.text = it.info
                    binding.detailLocationText.text = it.location
                    binding.detailDateText.text = it.dateAndTime
            }
        )





    }

}