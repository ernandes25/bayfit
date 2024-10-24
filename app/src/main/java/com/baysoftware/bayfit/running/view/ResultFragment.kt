package com.baysoftware.bayfit.running.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.baysoftware.bayfit.R
import com.baysoftware.bayfit.databinding.FragmentResultBinding
import com.baysoftware.bayfit.util.getTimeStringFromDouble

class ResultFragment : Fragment() {

    private lateinit var binding: FragmentResultBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_result, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonReturn.setOnClickListener { activity?.finish() }

        binding.timeEnd.text = arguments?.getString("endTime")

        binding.restEnd.text = arguments?.getDouble("endRest")?.getTimeStringFromDouble()
    }
}