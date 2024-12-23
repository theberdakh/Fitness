package com.theberdakh.fitness.feature.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.theberdakh.fitness.R
import com.theberdakh.fitness.databinding.ScreenProfileBinding
import com.theberdakh.fitness.feature.profile.adapter.ProfileAdapter
import com.theberdakh.fitness.feature.profile.model.ProfileItem
import com.theberdakh.fitness.feature.profile.model.ProfileItemType

class ProfileScreen : Fragment(R.layout.screen_profile) {
    private val viewBinding by viewBinding(ScreenProfileBinding::bind)
    private val profileItems = listOf(
        ProfileItem("Подписки", ProfileItemType.SUBSCRIPTIONS, R.color.black),
        ProfileItem("О нас", ProfileItemType.ABOUT_US, R.color.black),
        ProfileItem("Выйти", ProfileItemType.LOGOUT, R.color.bright_red)
    )
    private val profileAdapter = ProfileAdapter(profileItems)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()

    }

    private fun setUpRecyclerView() {
        profileAdapter.setOnItemClickListener { profileItem ->
            when (profileItem.type) {
                ProfileItemType.SUBSCRIPTIONS -> {
                    // Handle subscriptions click
                }
                ProfileItemType.ABOUT_US -> {
                    // Handle about us click
                }
                ProfileItemType.LOGOUT -> {
                    // Handle logout click
                }
            }
        }
        viewBinding.rvProfile.adapter = profileAdapter
    }
}