package com.example.activitiesappfigma.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.activitiesappfigma.MainActivity
import com.example.activitiesappfigma.MainViewModel
import com.example.activitiesappfigma.databinding.FragmentEventdetailBinding
import com.example.activitiesappfigma.databinding.FragmentLoginBinding

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

        var detailImage = binding.detailImage
        var detailName = binding.detailEventnameText
        var detailProfile = binding.detailProfilenameText
        var detailLocation = binding.detailLocationText
        var detailDate = binding.detailDateText
        var detailTime = binding.detailTimeText
        var detailMemberText = binding.detailMemberText
        var detailMemberCount = binding.detailMembercountText
        var detailDescriptionTitle = binding.detailDescriptionTitle
        var detailDescriptionText = binding.detailDescriptionText
        var detailPhotosTitle = binding.detailPhotosTitle
        var detailJoinEventButton = binding.detailJoinEventButton

    }

}