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

        setupNumberPicker()

        binding.ok.setOnClickListener {
            saveDataUser()
            findNavController().navigate(R.id.fragment_home)
        }
    }

    private fun setupNumberPicker() {
        //TODO: consertar erro de não carrgar os valores do usuário após algumas tentativas

        lifecycleScope.launch {
            val user = UserManager.getInstance().readTimerConfiguration(requireContext())
            binding.numberPickerMin.value = user.minute
            binding.numberPickerSec.value = user.second
        }
    }

    private fun saveDataUser() {
        val vlrDoMinutoDoPicker = binding.numberPickerMin.value
        val vlrDoSegundoDoPicker = binding.numberPickerSec.value

        lifecycleScope.launch {
            UserManager.getInstance().saveDataUser(
                requireContext(),
                minute = vlrDoMinutoDoPicker,
                second = vlrDoSegundoDoPicker
            )
        }
    }
}