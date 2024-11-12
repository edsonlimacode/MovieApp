package com.edsonlima.flixapp.presenter.auth.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.edsonlima.flixapp.R
import com.edsonlima.flixapp.databinding.FragmentLoginBinding
import com.edsonlima.flixapp.presenter.MainActivity
import com.edsonlima.flixapp.utils.FirebaseHelper
import com.edsonlima.flixapp.utils.StateView
import com.edsonlima.flixapp.utils.hideKeyboard
import com.edsonlima.flixapp.utils.initToolBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(this).load(
            R.drawable.loading
        ).into(binding.loading);

        initToolBar(binding.tbLogin)

        initListeners()
    }

    private fun initListeners() {

        binding.btnLogin.setOnClickListener {
            validate()
        }

        binding.btnForgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotFragment)
        }

    }

    private fun validate() {

        val email = binding.editEmailLogin.text.toString().trim()
        val password = binding.editPasswordLogin.text.toString().trim()

        if (email.isNotEmpty()) {
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                if (password.isNotEmpty()) {
                    if (password.length > 3) {
                        hideKeyboard()
                        login(email, password)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.password_weak),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.empty_password), Toast.LENGTH_LONG
                    )
                        .show()
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.invalid_email),
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(requireContext(), getString(R.string.empty_email), Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun login(email: String, password: String) {

        loginViewModel.login(email, password).observe(viewLifecycleOwner) { stateView ->

            when (stateView) {
                is StateView.Loading -> {
                    binding.loading.isVisible = true
                }

                is StateView.Success -> {
                    binding.loading.isVisible = false

                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    requireActivity().finish()

                }

                is StateView.Error -> {
                    binding.loading.isVisible = false
                    Toast.makeText(
                        requireContext(),
                        FirebaseHelper.validError(stateView.message ?: ""),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}