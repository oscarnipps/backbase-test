package com.backbase.assignment.ui.playingnow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.backbase.assignment.R
import com.backbase.assignment.databinding.FragmentPlayingNowBinding

class PlayingNowFragment  : Fragment() {

    private lateinit var binding : FragmentPlayingNowBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_playing_now, container, false)

        return binding.root
    }
}