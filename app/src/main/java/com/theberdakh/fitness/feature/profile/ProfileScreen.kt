package com.theberdakh.fitness.feature.profile

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.theberdakh.fitness.R
import com.theberdakh.fitness.data.preferences.LocalUserPreference
import com.theberdakh.fitness.databinding.ScreenProfileBinding
import com.theberdakh.fitness.feature.auth.viewmodel.AuthViewModel
import com.theberdakh.fitness.feature.auth.viewmodel.GetProfileUiState
import com.theberdakh.fitness.feature.auth.viewmodel.LogOutUiState
import com.theberdakh.fitness.feature.common.dialog.UniversalDialog
import com.theberdakh.fitness.feature.common.error.ErrorDelegate
import com.theberdakh.fitness.feature.profile.adapter.ProfileAdapter
import com.theberdakh.fitness.feature.profile.model.ProfileItem
import com.theberdakh.fitness.feature.profile.model.ProfileItemType
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileScreen : Fragment(R.layout.screen_profile) {
    private val viewBinding by viewBinding(ScreenProfileBinding::bind)
    private var profileItems: List<ProfileItem> = emptyList()
    private var profileAdapter: ProfileAdapter? = null
    private val viewModel by viewModel<AuthViewModel>()
    private val errorDelegate by inject<ErrorDelegate>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViews()
        getProfile()
    }

    private fun getProfile() {
        viewModel.getProfileUiState.onEach {
            when(it){
                is GetProfileUiState.Error -> errorDelegate.errorToast(it.message)
                GetProfileUiState.Loading -> handleLoading()
                is GetProfileUiState.Success -> handleSuccess(it.data)
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleSuccess(data: LocalUserPreference) {
        viewBinding.tbProfile.title = data.name
        viewBinding.tbProfile.subtitle = data.phone
    }

    private fun handleLoading() {

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
                                lifecycleScope.launch {
                                    viewModel.logOutUiState.collect { logOutState ->
                                        when(logOutState){
                                            LogOutUiState.Error -> {
                                                Log.i("Logout", "logout: Error")
                                                handleError("Log out error")
                                            }
                                            LogOutUiState.Loading -> {
                                                Log.i("Logout", "logout: Loading")
                                                handleLoading()
                                            }
                                            is LogOutUiState.Success -> {
                                                Log.i("Logout", "logout: Success")
                                                findNavController().navigate(R.id.action_mainScreen_to_LogoScreen)
                                            }
                                        }
                                    }
                                }

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