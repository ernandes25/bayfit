package com.baysoftware.bayfit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.datastore.core.DataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.baysoftware.bayfit.databinding.FragmentTimerSetterBinding
import kotlinx.coroutines.launch
import java.util.prefs.Preferences

class TimerSetterFragment : Fragment() {
    private lateinit var binding: FragmentTimerSetterBinding
    private lateinit var userManager: UserManager
//    private lateinit var numberPicker: NumberPicker


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
    private lateinit var dataStore: DataStore<Preferences>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userManager = UserManager(requireContext())

        
        binding.numberPickerMin.setOnValueChangedListener { picker, oldVal, newVal ->
            setOnfindNavController().navigate(R.id.) "Selected Value : $newVal"}
        
        binding.ok.setOnClickListener {
            saveDataUser()
            findNavController().navigate(R.id.fragment_home)
            readDataUser()
            return@setOnClickListener
      }
 }

//    private fun setupNumberPicker() {
//        val numberPicker = binding.numberPickerMin
//
//        numberPicker.wrapSelectorWheel = true
//        numberPicker.setOnValueChangedListener { picker, oldVal, newVal ->
//            val text = "Changed from $oldVal to $newVal"
//            Toast.makeText(requireActivity(), text, Toast.LENGTH_SHORT).show()
//
//        }
//
//    }

    private fun saveDataUser() {
        val vlrDoMinutoDoPicker = binding.numberPickerMin.value
        val vlrDoSegundoDoPicker = binding.numberPickerSec.value


        lifecycleScope.launch {
            userManager.saveDataUser(
                minute = vlrDoMinutoDoPicker,
                second = vlrDoSegundoDoPicker
            )
        }
    }


    private fun readDataUser() {
        lifecycleScope.launch {
            val user = userManager.readDataUser()

            binding.numberPickerMin.value
            binding.numberPickerSec.value

            println("minute:${user.minute}, second: ${user.second}")

        }
    }

}



