package com.example.activitiesappfigma.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.activitiesappfigma.MainViewModel
import com.example.activitiesappfigma.R
import com.example.activitiesappfigma.adapter.CategoryAdapter
import com.example.activitiesappfigma.databinding.FragmentCategoryBinding

class CategoryFragment : Fragment() {

    lateinit var binding: FragmentCategoryBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoryAdapter = CategoryAdapter()
        binding.categoryRecycler.adapter = categoryAdapter

        viewModel.category.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {
                categoryAdapter.submitList(it)
            }
        )

        binding.categoryContinueButton.setOnClickListener {
            findNavController().navigate(R.id.eventListFragment)
        }

    }

}