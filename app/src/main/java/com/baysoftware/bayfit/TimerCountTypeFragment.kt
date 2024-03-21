package com.baysoftware.bayfit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.baysoftware.bayfit.databinding.FragmentTimerCountTypeBinding
import kotlinx.coroutines.launch


class TimerCountTypeFragment : Fragment() {
    private lateinit var binding: FragmentTimerCountTypeBinding
    lateinit var radioButton: RadioButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_timer_count_type,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.radioButton1.setOnClickListener {

            val selectedButton: Int = binding.radioGroup.checkedRadioButtonId
            radioButton = binding.radioGroup.findViewById(selectedButton)
            Toast.makeText(context, radioButton.text, Toast.LENGTH_SHORT).show()
        }

        binding.buttonOkcountType.setOnClickListener {

            lifecycleScope.launch {
                val botaoSelecionado: UserManager.TimerMode

                if (binding.radioButton1.isSelected) {

                    botaoSelecionado = UserManager.TimerMode.FREE
                    UserManager.getInstance().saveTimerMode(requireContext(), botaoSelecionado)

                } else if (binding.radioButton2.isSelected) {

                    botaoSelecionado = UserManager.TimerMode.PREDEFINED
                    UserManager.getInstance().saveTimerMode(requireContext(), botaoSelecionado)
                } else {
                    //TODO: Mostrar Toast. Selecione uma opção
                }


            }
            findNavController().navigate(R.id.fragment_home)
        }


        binding.radioButton2.setOnClickListener {
            val selectedButton: Int = binding.radioGroup.checkedRadioButtonId
            radioButton = binding.radioGroup.findViewById(selectedButton)
            Toast.makeText(context, radioButton.text, Toast.LENGTH_SHORT).show()
            binding.buttonOkcountType.setOnClickListener {
                findNavController().navigate(R.id.fragment_timer_setter)
            }
        }
    }
}

