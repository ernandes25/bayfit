package com.baysoftware.bayfit

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.RECEIVER_NOT_EXPORTED
import androidx.core.content.ContextCompat.registerReceiver
import androidx.core.content.getSystemService
import androidx.core.os.bundleOf
import androidx.core.view.isInvisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.baysoftware.bayfit.databinding.FragmentTimerBinding
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class TimerFragment : Fragment() {

    private lateinit var binding: FragmentTimerBinding
    private lateinit var increasingTimerServiceIntent: Intent
    private lateinit var decreasingTimerServiceIntent: Intent

    private var increasingTime = 0.00

    private var timerStarted = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_timer, container, false)
        increasingTimerServiceIntent = Intent(requireContext(), IncreasingTimerService::class.java)
        increasingTimerServiceIntent.putExtra(TimerService.TIME_EXTRA, increasingTime)
        decreasingTimerServiceIntent = Intent(requireContext(), DecreasingTimerService::class.java)

        lifecycleScope.launch {
            //TODO: pegar configuração de tempo antes de iniciar o serviço para saber se o tempo é crescente ou decrescente

//            UserManager.getInstance().saveTimerMode(requireContext())
//            UserManager.getInstance().readTimerMode(requireContext())



        /*    val selectRadioButton1 = UserManager.getInstance()
                .saveTimerMode(mode = UserManager.TimerMode.FREE, context = requireContext())
            val selectRadioButton2 = UserManager.getInstance()
                .saveTimerMode(mode = UserManager.TimerMode.PREDEFINED, context = requireContext())*/


            val timerConfiguration = UserManager.getInstance().readTimerConfiguration(requireContext())
            val decreasingTime = timerConfiguration.minute.toDouble() * 60 + timerConfiguration.second.toDouble()


            decreasingTimerServiceIntent.putExtra(TimerService.TIME_EXTRA, decreasingTime)

        }
        registerReceiver(
            requireContext(),
            updateIncreasingTime,
            IntentFilter(IncreasingTimerService.TIMER_UPDATE),
            RECEIVER_NOT_EXPORTED
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.resumeButton.setOnLongClickListener {
            stopTrainingSession()
            return@setOnLongClickListener true
        }

        binding.pauseButton.setOnClickListener { pauseTimer() }
        binding.resumeButton.setOnClickListener { resumeTraining() }

        requireActivity().startService(increasingTimerServiceIntent)
    }

/*    private fun ??????() {
        //TODO: consertar erro de não carrgar os valores do usuário após algumas tentativas

        lifecycleScope.launch {
            val user = UserManager.getInstance().readTimerMode(requireContext())
            UserManager.getInstance().readTimerMode(requireContext())
            binding.pegar contexto FRAGMENT_TIMER_COUNT_TYPE
        }
    }*/


    private fun stopTrainingSession() {

        requireActivity().stopService(increasingTimerServiceIntent)
        requireActivity().stopService(decreasingTimerServiceIntent)
        requireActivity().unregisterReceiver(updateIncreasingTime)

        val bundle = bundleOf("endTime" to binding.secondaryTimer.text)
        findNavController().navigate(R.id.action_timerFragment_to_fragment_result, bundle)
    }

    private fun vibrate(duration: Long = 500) {
        val vibrator = requireContext().getSystemService() as? Vibrator
        vibrator?.vibrate(
            VibrationEffect.createOneShot(
                duration,
                VibrationEffect.DEFAULT_AMPLITUDE
            )
        )
    }

    private fun pauseTimer() {
        timerStarted = false
        startDecreasingTimer()
        binding.textRest.isInvisible = false
        binding.pauseButton.isInvisible = true
        binding.primaryTimer.setTextColor(resources.getColor(R.color.green, null))
        binding.secondaryTimer.isInvisible = false
        binding.secondaryTimer.text = binding.primaryTimer.text
    }

    private fun resumeTraining() {
        timerStarted = true
        requireActivity().stopService(decreasingTimerServiceIntent)
        binding.resumeButton.isInvisible = true
        binding.pauseButton.isInvisible = false
        binding.primaryTimer.setTextColor(resources.getColor(R.color.white, null))
        binding.primaryTimer.text = binding.secondaryTimer.text
        binding.secondaryTimer.isInvisible = true
    }

    private fun startDecreasingTimer() {
        registerReceiver(
            requireContext(),
            updateDecreasingTime,
            IntentFilter(DecreasingTimerService.TIMER_UPDATE),
            RECEIVER_NOT_EXPORTED
        )
        requireActivity().startService(decreasingTimerServiceIntent)
    }

    // TODO: este método será utilizado quando o usuário finalizar o treino
    //  @Suppress("unused")
    private fun stopTimer() {

        requireActivity().stopService(decreasingTimerServiceIntent)
        timerStarted = false
    }

    private val updateIncreasingTime: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            increasingTime = intent.getDoubleExtra(TimerService.TIME_EXTRA, 0.0)
            val time = getTimeStringFromDouble(increasingTime)
            if (timerStarted) {
                binding.primaryTimer.text = time
            } else {
                binding.secondaryTimer.text = time
            }
        }
    }

    private val updateDecreasingTime: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val time = intent.getDoubleExtra(TimerService.TIME_EXTRA, 0.0)


            if (time == 0.00) {
                stopTimer()
                binding.primaryTimer.text
                binding.resumeButton.isInvisible = false
                binding.textRest.isInvisible = true
                vibrate()

            }
            binding.primaryTimer.text = getTimeStringFromDouble(time)
        }
    }

    // TODO: colocar este método em arquivo de extension functions
    private fun getTimeStringFromDouble(time: Double): String {
        val resultInt = time.roundToInt()
        val hours = resultInt % TOTAL_MINUTES_IN_DAY / TOTAL_MINUTES_IN_HOUR
        val minutes = resultInt % TOTAL_MINUTES_IN_DAY % TOTAL_MINUTES_IN_HOUR / TOTAL_HOURS
        val seconds = resultInt % TOTAL_MINUTES_IN_DAY % TOTAL_MINUTES_IN_HOUR % TOTAL_HOURS
        return makeTimeString(hours, minutes, seconds)
    }

    // TODO: colocar este método em arquivo de extension functions
    private fun makeTimeString(hour: Int, min: Int, sec: Int): String =
        String.format("%02d:%02d:%02d", hour, min, sec)

    companion object {
        const val TOTAL_MINUTES_IN_DAY = 86400
        const val TOTAL_MINUTES_IN_HOUR = 3600
        const val TOTAL_HOURS = 60
    }
}