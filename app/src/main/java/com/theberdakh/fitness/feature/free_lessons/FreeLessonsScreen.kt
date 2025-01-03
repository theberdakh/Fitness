package com.theberdakh.fitness.feature.free_lessons

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.theberdakh.fitness.R
import com.theberdakh.fitness.databinding.ScreenFreeLessonsBinding
import by.kirich1409.viewbindingdelegate.viewBinding

class FreeLessonsScreen: Fragment(R.layout.screen_free_lessons) {
    private val viewBinding by viewBinding(ScreenFreeLessonsBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.tbFreeLessons.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

    }
}