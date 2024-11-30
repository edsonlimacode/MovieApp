package com.edsonlima.flixapp.presenter.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.edsonlima.flixapp.R
import com.edsonlima.flixapp.databinding.FragmentOnBoardingBinding
import com.edsonlima.flixapp.utils.navigateAnimated
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingFragment : Fragment() {

    private lateinit var binding: FragmentOnBoardingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentOnBoardingBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
    }

    private fun initListeners() {
        binding.btnOnBoard.setOnClickListener {
            findNavController().navigateAnimated(R.id.action_onBoardingFragment_to_homeAuthFragment)
        }
    }
}