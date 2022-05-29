package com.backbase.assignment.ui.mostpopular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.backbase.assignment.R
import com.backbase.assignment.databinding.FragmentMostPopularBinding

class MostPopularFragment   : Fragment() {

    private lateinit var binding : FragmentMostPopularBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_most_popular, container, false)

        return binding.root
    }
}