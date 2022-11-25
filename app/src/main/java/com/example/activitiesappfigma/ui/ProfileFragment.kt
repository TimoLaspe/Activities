package com.example.activitiesappfigma.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.activitiesappfigma.MainActivity
import com.example.activitiesappfigma.MainViewModel
import com.example.activitiesappfigma.R
import com.example.activitiesappfigma.databinding.FragmentLoginBinding
import com.example.activitiesappfigma.databinding.FragmentProfileBinding

class ProfileFragment: Fragment(){

    lateinit var binding: FragmentProfileBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater)
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


        binding.profileCategoryButton.setOnClickListener {
            findNavController().navigate(R.id.categoryFragment)
        }

        binding.logoutTextButton.setOnClickListener {
            viewModel.logout()
        }

    }

}