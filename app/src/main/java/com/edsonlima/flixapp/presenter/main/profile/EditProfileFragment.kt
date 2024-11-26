package com.edsonlima.flixapp.presenter.main.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.edsonlima.flixapp.R
import com.edsonlima.flixapp.databinding.FragmentEditProfileBinding
import com.edsonlima.flixapp.domain.model.User
import com.edsonlima.flixapp.utils.FirebaseHelper
import com.edsonlima.flixapp.utils.StateView
import com.edsonlima.flixapp.utils.initToolBar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment : Fragment() {

    private lateinit var binding: FragmentEditProfileBinding

    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentEditProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolBar(binding.tbProfile)
        initSpinner()
        initObservers()
        initListeners()
        getUserById()
    }

    private fun initSpinner() {
        binding.spGenre.adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.genre,
            R.layout.item_profile_genre_select
        )
    }

    private fun getUserById() {
        profileViewModel.getUserById(FirebaseHelper.getUserId())
    }

    private fun initListeners() {
        binding.btnUpdateProfile.setOnClickListener {
            validate()
        }
    }

    private fun validate() {

        val name = binding.editNameProfile.text.toString()
        val lastName = binding.editLastNameProfile.text.toString()
        val genre = binding.spGenre.selectedItem.toString()

        if (name.isEmpty()) {
            Toast.makeText(requireContext(), "Nome é obrigatório", Toast.LENGTH_LONG).show()
            return
        }

        if (lastName.isEmpty()) {
            Toast.makeText(requireContext(), "Sobrenome é obrigatório", Toast.LENGTH_LONG).show()
            return
        }

        if (genre.isEmpty() || binding.spGenre.selectedItemPosition == 0) {
            Toast.makeText(requireContext(), "Gênero é obrigatório", Toast.LENGTH_LONG).show()
            return
        }

        val user = User(
            name = name,
            genre = genre,
            lastName = lastName
        )

        profileViewModel.update(user)


    }

    private fun initObservers() {
        profileViewModel.loadingState.observe(viewLifecycleOwner) { sateView ->
            when (sateView) {
                is StateView.Loading -> {
                    binding.loading.isVisible = true
                }

                is StateView.Success -> {
                    binding.loading.isVisible = false
                    Snackbar.make(
                        binding.root,
                        "Perfile atualizado com sucesso!",
                        Snackbar.LENGTH_LONG
                    ).show()
                }

                is StateView.Error -> {
                    binding.loading.isVisible = false
                    Toast.makeText(requireContext(), sateView.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        profileViewModel.userState.observe(viewLifecycleOwner) { stateView ->

            when (stateView) {

                is StateView.Loading -> {
                    binding.pbProfile.isVisible = true
                }
                is StateView.Success -> {

                    binding.pbProfile.isVisible = false

                    stateView.data?.let {
                        binding.editNameProfile.setText(it.name)
                        binding.editLastNameProfile.setText(it.lastName)

                        val options = resources.getStringArray(R.array.genre)
                        val position = options.indexOf(it.genre)

                        if (position != -1) {
                            binding.spGenre.setSelection(position)
                        }
                    }

                    binding.editEmailProfile.setText(FirebaseHelper.getAuth().currentUser?.email)

                }
                is StateView.Error -> {
                    binding.pbProfile.isVisible = false
                    Toast.makeText(requireContext(), stateView.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}