package com.baysoftware.bayfit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.baysoftware.bayfit.databinding.FragmentTimerSetterBinding

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

        binding.ok.setOnClickListener {

            findNavController().navigate(R.id.fragment_home)
        }

    }
//TODO: além de retornar para a fragment_hom, ao clicar em OK(já está retornando), deverá armazenar o
    //tempo na SHARED PREFERENCE ou na DATESTORE

}















