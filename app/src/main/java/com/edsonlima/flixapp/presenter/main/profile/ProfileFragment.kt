package com.edsonlima.flixapp.presenter.main.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.edsonlima.flixapp.R
import com.edsonlima.flixapp.databinding.BottomSheetLogoutBinding
import com.edsonlima.flixapp.databinding.FragmentProfileBinding
import com.edsonlima.flixapp.domain.model.ItemMenuProfile
import com.edsonlima.flixapp.domain.model.Type
import com.edsonlima.flixapp.domain.model.User
import com.edsonlima.flixapp.presenter.auth.AuthActivity
import com.edsonlima.flixapp.presenter.auth.logout.LogoutViewModel
import com.edsonlima.flixapp.utils.FirebaseHelper
import com.edsonlima.flixapp.utils.StateView
import com.edsonlima.flixapp.utils.navigateToNavGraph
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private lateinit var menuProfileAdapter: MenuProfileAdapter

    private val logoutViewModel: LogoutViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()
        getUserById()
        initObservers()

        binding.textProfileEmail.text = FirebaseHelper.getAuth().currentUser?.email
    }

    private fun getUserById() {
        logoutViewModel.getUserById(FirebaseHelper.getUserId())
    }

    private fun setData(user: User) {
        binding.textProfileName.text = user.name
    }

    private fun initObservers() {
        logoutViewModel.userState.observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {}
                is StateView.Success -> {
                    stateView.data?.let {
                        setData(stateView.data)
                    }
                }

                is StateView.Error -> {
                    Toast.makeText(requireContext(), stateView.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initRecycler() {

        menuProfileAdapter = MenuProfileAdapter(
            items = ItemMenuProfile.items,
            onClickListener = { type ->
                when (type) {
                    Type.EDIT -> {
                        findNavController().navigate(R.id.action_menuProfile_to_editProfileFragment2)
                    }

                    Type.NOTIFICATION -> TODO()
                    Type.DOWNLOAD -> {
                        val bottomNavigation =
                            requireActivity().findViewById<BottomNavigationView>(R.id.bnvMain)
                        bottomNavigation.selectedItemId = R.id.menuDownload
                    }

                    Type.SECURITY -> TODO()
                    Type.LANGUAGE -> TODO()
                    Type.DARK_MODE -> TODO()
                    Type.HELP -> TODO()
                    Type.PRIVACY_POLITIC -> TODO()
                    Type.LOGOUT -> {
                        initBottomSheet()
                    }

                }
            }
        )

        binding.rvProfile.adapter = menuProfileAdapter
        binding.rvProfile.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initBottomSheet() {

        val bottomSheet = BottomSheetDialog(requireContext())

        val bottomSheetLogoutBinding = BottomSheetLogoutBinding.inflate(layoutInflater, null, false)

        bottomSheet.setContentView(bottomSheetLogoutBinding.root)

        bottomSheetLogoutBinding.btnLogout.setOnClickListener {
            logout()
            bottomSheet.dismiss()
        }

        bottomSheetLogoutBinding.btnCancel.setOnClickListener {
            bottomSheet.dismiss()
        }

        bottomSheet.show()
    }

    private fun logout() {
        logoutViewModel.logout()
        activity?.finish()

        requireActivity().navigateToNavGraph(AuthActivity::class.java, R.id.loginFragment)
    }

}