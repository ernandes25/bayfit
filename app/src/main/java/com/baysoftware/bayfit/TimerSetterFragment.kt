package com.baysoftware.bayfit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.baysoftware.bayfit.databinding.FragmentTimerSetterBinding
import kotlinx.coroutines.launch


class TimerSetterFragment : Fragment() {

    private lateinit var binding: FragmentTimerSetterBinding
    private lateinit var userManager: UserManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_timer_setter,
            container,
            false
        )
        return binding.root



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ok.setOnClickListener {

            findNavController().navigate(R.id.fragment_home)
            binding.numberPickerMin.setOnContextClickListener {
                initListeners()
                return@setOnContextClickListener true
            }
            userManager =   UserManager(requireContext(),  )

            initListeners()
            readDataUser()
        }


    }

    private fun initListeners () {
        binding.ok.setOnClickListener { saveDataUser() }


    }

    private fun saveDataUser() {
        val minute = binding.numberPickerMin.context
        val second = binding.numberPickerSec.context

        lifecycleScope.launch { userManager.saveDataUser(minute, second) }
    }



    private fun readDataUser() {
lifecycleScope.launch {
    val user = userManager.readDataUser()

    binding.numberPickerMin.context.getText(user.minute as Int)
    binding.numberPickerSec.context.getText(user.second)

}
    }

}



