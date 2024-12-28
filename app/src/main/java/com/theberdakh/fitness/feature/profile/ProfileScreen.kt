package com.theberdakh.fitness.feature.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.theberdakh.fitness.R
import com.theberdakh.fitness.core.network.model.NetworkResponse
import com.theberdakh.fitness.core.network.model.mobile.Profile
import com.theberdakh.fitness.databinding.ScreenProfileBinding
import com.theberdakh.fitness.feature.auth.viewmodel.AuthViewModel
import com.theberdakh.fitness.feature.common.dialog.UniversalDialog
import com.theberdakh.fitness.feature.profile.adapter.ProfileAdapter
import com.theberdakh.fitness.feature.profile.model.ProfileItem
import com.theberdakh.fitness.feature.profile.model.ProfileItemType
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileScreen : Fragment(R.layout.screen_profile) {
    private val viewBinding by viewBinding(ScreenProfileBinding::bind)
    private var profileItems: List<ProfileItem> = emptyList()
    private var profileAdapter: ProfileAdapter? = null
    private val viewModel by viewModel<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViews()
        getProfile()
    }

    private fun getProfile() {
        viewModel.getProfile()
        viewModel.getProfileState.onEach {
            when (it) {
                is NetworkResponse.Error -> handleError(it.message)
                NetworkResponse.Initial -> handleInitial()
                NetworkResponse.Loading -> handleLoading()
                is NetworkResponse.Success -> handleSuccess(it.data)
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleSuccess(data: Profile) {
        viewBinding.tbProfile.title = data.name
        viewBinding.tbProfile.subtitle = data.phone
    }

    private fun handleLoading() {

    }

    private fun handleInitial() {

    }

    private fun handleError(message: String) {

    }

    private fun setUpViews() {
        profileItems = listOf(
            ProfileItem(getString(R.string.subscriptions), ProfileItemType.SUBSCRIPTIONS, R.color.black),
            ProfileItem(getString(R.string.about_us), ProfileItemType.ABOUT_US, R.color.black),
            ProfileItem(getString(R.string.exit), ProfileItemType.LOGOUT, R.color.bright_red)
        )

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {

        profileAdapter = ProfileAdapter(profileItems)
        profileAdapter?.let {
            it.setOnItemClickListener {
                profileItem ->
                when (profileItem.type) {
                    ProfileItemType.SUBSCRIPTIONS -> {
                        findNavController().navigate(R.id.action_mainScreen_to_subscriptionsScreen)
                    }
                    ProfileItemType.ABOUT_US -> {
                        findNavController().navigate(R.id.action_mainScreen_to_aboutScreen)
                    }
                    ProfileItemType.LOGOUT -> {
                        UniversalDialog.Builder(requireContext())
                            .setTitle(getString(R.string.exit))
                            .setMessage(getString(R.string.message_are_your_sure_to_leave))
                            .setPrimaryButton(getString(R.string.exit)) {
                                viewModel.logout()
                                findNavController().navigate(R.id.action_mainScreen_to_LogoScreen)
                            }
                            .setSecondaryButton(getString(R.string.stay))
                            .build().show()
                            // Handle logout click
                    }
                }
            }
        }
        viewBinding.rvProfile.adapter = profileAdapter

    }
}