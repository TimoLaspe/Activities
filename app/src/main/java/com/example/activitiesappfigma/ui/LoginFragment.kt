package com.example.activitiesappfigma.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.activitiesappfigma.MainActivity
import com.example.activitiesappfigma.MainViewModel
import com.example.activitiesappfigma.R
import com.example.activitiesappfigma.databinding.FragmentLoginBinding

class LoginFragment: Fragment(){

    lateinit var binding: FragmentLoginBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.lifecycleScope?.launchWhenCreated {
            (activity as MainActivity).hideBottomNav()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.startLoginButton.setOnClickListener {
            val email = binding.loginEmailTextInput.text.toString()
            val password = binding.loginPasswordTextInput.text.toString()

            if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
                viewModel.login(email, password)
            }
        }

        viewModel.currentUser.observe(
            viewLifecycleOwner,
            Observer {
                if (it != null) {
                    findNavController().navigate(R.id.profileFragment)
                }
            }
        )

        binding.startSignupButton.setOnClickListener {
            findNavController().navigate(R.id.signUpFragment)
        }


    }

}