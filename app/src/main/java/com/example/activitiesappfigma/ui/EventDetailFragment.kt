package com.example.activitiesappfigma.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.activitiesappfigma.MainActivity
import com.example.activitiesappfigma.MainViewModel
import com.example.activitiesappfigma.R
import com.example.activitiesappfigma.adapter.EventAdapter
import com.example.activitiesappfigma.adapter.PhotoAdapter
import com.example.activitiesappfigma.data.model.Event
import com.example.activitiesappfigma.data.model.Photo
import com.example.activitiesappfigma.databinding.FragmentEventdetailBinding


class EventDetailFragment : Fragment() {

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

        viewModel.photo.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {
                photoAdapter.submitList(it)
            }
        )

        var memberCounter = 10

        binding.detailMembercountText.text = memberCounter.toString()

        binding.detailJoinEventButton.setOnClickListener {
            memberCounter ++
                binding.detailMembercountText.text = memberCounter.toString()
            val toast = Toast.makeText(context,"Super, du bist dem Event beigetreten!", Toast.LENGTH_SHORT)
            toast.show()
        }
      //  viewModel.getEventDataWithWeater(lon,lat)

        viewModel.event.observe(
            viewLifecycleOwner,
            Observer {
                binding.detailImage.load(it.image)
                binding.detailEventnameText.text = it.name
                binding.detailDescriptionText.text = it.info
                binding.detailLocationText.text = it.location
                binding.detailDateText.text = it.dateAndTime
                binding.detailProfilenameText.text = "Profilname"
                binding.detailTimeText.text = "19 Uhr"
            }
        )


    }

}