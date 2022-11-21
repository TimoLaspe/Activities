package com.example.activitiesappfigma.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.activitiesappfigma.MainViewModel
import com.example.activitiesappfigma.R
import com.example.activitiesappfigma.data.model.Profile
import com.example.activitiesappfigma.databinding.FragmentSignupBinding

class SignUpFragment : Fragment() {

    lateinit var binding: FragmentSignupBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signupButtonBack.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }

        binding.signupButtonSignUp.setOnClickListener {
            signUp()
        }

        viewModel.currentUser.observe(
            viewLifecycleOwner,
            Observer {
                if (it != null) {
                    findNavController().navigate(R.id.profileFragment)
                }
            }
        )

    }

    private fun signUp() {
        val email = binding.signupEmailTextInput.text.toString()
        val password = binding.signupPasswordTextInput.text.toString()
        val name = binding.signupNameTextInput.text.toString()
        val info = binding.signupInfoTextInput.text.toString()
        val location = binding.signupLocationTextInput.text.toString()

        if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
            val newProfile = Profile(
                name = name,
                info = info,
                location = location
            )
            viewModel.signUp(email, password, newProfile)
        }
    }
}