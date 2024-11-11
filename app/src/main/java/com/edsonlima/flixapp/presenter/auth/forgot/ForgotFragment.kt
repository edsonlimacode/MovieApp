package com.edsonlima.flixapp.presenter.auth.forgot

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
import com.edsonlima.flixapp.databinding.FragmentForgotBinding
import com.edsonlima.flixapp.utils.StateView
import com.edsonlima.flixapp.utils.hideKeyboard
import com.edsonlima.flixapp.utils.initToolBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotFragment : Fragment() {

    private lateinit var binding: FragmentForgotBinding

    private val recoverViewModel: ForgotViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentForgotBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(this).load(
            R.drawable.loading
        ).into(binding.loadingForgot);

        initToolBar(binding.tbForgot)

        initListeners()
    }

    private fun initListeners() {

        binding.btnRecover.setOnClickListener {
            validate()
        }

    }

    private fun validate() {

        val email = binding.editEmailRecorver.text.toString().trim()

        if (email.isNotEmpty()) {
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                hideKeyboard()
                recover(email)

            } else {
                Toast.makeText(requireContext(), "E-mail inválido", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireContext(), "E-mail é obrigatório", Toast.LENGTH_SHORT).show()
        }
    }

    private fun recover(email: String) {

        recoverViewModel.forgot(email).observe(viewLifecycleOwner) { stateView ->

            when (stateView) {
                is StateView.Loading -> {
                    binding.loadingForgot.isVisible = true
                }

                is StateView.Success -> {
                    binding.loadingForgot.isVisible = false
                    Toast.makeText(
                        requireContext(),
                        "Acesse seu e-mail de cadastro para alterar a senha",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }

                is StateView.Error -> {
                    binding.loadingForgot.isVisible = false
                    Toast.makeText(requireContext(), stateView.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}