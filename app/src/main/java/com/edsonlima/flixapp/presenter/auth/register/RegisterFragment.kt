package com.edsonlima.flixapp.presenter.auth.register

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.edsonlima.flixapp.R
import com.edsonlima.flixapp.databinding.FragmentRegisterBinding
import com.edsonlima.flixapp.presenter.auth.login.LoginViewModel
import com.edsonlima.flixapp.utils.StateView
import com.edsonlima.flixapp.utils.hideKeyboard
import com.edsonlima.flixapp.utils.initToolBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(this).load(
            R.drawable.loading
        ).into(binding.loading);

        initToolBar(binding.tbRegister)

        initListeners()
    }

    private fun initListeners() {

        binding.btnRegister.setOnClickListener {
            validate()
        }

    }

    private fun validate() {

        val email = binding.editEmailRegister.text.toString().trim()
        val password = binding.editPasswordRegister.text.toString().trim()

        if (email.isNotEmpty()) {
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                if (password.isNotEmpty()) {
                    if (password.length > 3) {
                        hideKeyboard()
                        register(email, password)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Senha deve conter no minímo 6 caracteres",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Senha é obrigatória", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(requireContext(), "E-mail inválido", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireContext(), "E-mail é obrigatório", Toast.LENGTH_SHORT).show()
        }
    }

    private fun register(email: String, password: String) {

        registerViewModel.register(email, password).observe(viewLifecycleOwner) { stateView ->

            when (stateView) {
                is StateView.Loading -> {
                    binding.loading.isVisible = true
                }

                is StateView.Success -> {
                    binding.loading.isVisible = false
                    Toast.makeText(requireContext(), "Cadastrado com sucesso", Toast.LENGTH_SHORT).show()
                }

                is StateView.Error -> {
                    binding.loading.isVisible = false
                    Toast.makeText(requireContext(), stateView.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}