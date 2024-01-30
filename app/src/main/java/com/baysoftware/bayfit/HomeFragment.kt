package com.baysoftware.bayfit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.baysoftware.bayfit.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO: checar se existe uma contagem acontecendo
        // caso não exista, não fazer nada
        // caso exista, navegar para a tela de contagem

        binding.imageButtonIniciar.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_home_to_fragment_timer)
            // TODO: setar SharedPreferences ongoingSession para true
        }
    }
}