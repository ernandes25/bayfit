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
    private lateinit var restTimerServiceIntent: Intent

    private var increasingTime = 0.00

        private var increasingRestTime = 0.00 // Eu-19/03
    private var timerStarted = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_timer, container, false)
        increasingTimerServiceIntent = Intent(requireContext(), IncreasingTimerService::class.java)
        increasingTimerServiceIntent.putExtra(TimerService.TIME_EXTRA, increasingTime)
        restTimerServiceIntent =Intent(requireContext(), DecreasingTimerService::class.java)

        lifecycleScope.launch {
            //TODO: pegar configuração de tempo antes de iniciar o serviço para saber se o tempo é crescente ou decrescente

            val timerConfiguration =
                UserManager.getInstance().readTimerConfiguration(requireContext())
            val restTime =
                timerConfiguration.minute.toDouble() * 60 + timerConfiguration.second.toDouble()

    //      restTimerServiceIntent = Intent(requireContext(), IncreasingTimerService::class.java)
            restTimerServiceIntent.putExtra(TimerService.TIME_EXTRA, restTime)


            //       val timerMode = UserManager.getInstance().readTimerMode(context = requireContext())
//            if (timerMode == UserManager.TimerMode.FREE) {
//
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

    //TODO: consertar erro de não carrgar os valores do usuário após algumas tentativas

    private fun stopTrainingSession() {

        requireActivity().stopService(increasingTimerServiceIntent)
        requireActivity().stopService(restTimerServiceIntent)
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

        if (  timerStarted == false) {
            startRestTimer()
            binding.textRest.isInvisible = false
            binding.pauseButton.isInvisible = true
            binding.primaryTimer.setTextColor(resources.getColor(R.color.green, null))
            binding.secondaryTimer.isInvisible = false
            binding.secondaryTimer.text = binding.primaryTimer.text
        }else{
            startRestTimerUp()
            stopTimer()
         // binding.textRest.isInvisible = false
            binding.pauseButton.isInvisible = false
            binding.primaryTimer.setTextColor(resources.getColor(R.color.green, null))
            binding.secondaryTimer.isInvisible = false
            binding.secondaryTimer.text = binding.primaryTimer.text


        }

    }

    private fun resumeTraining() {
        timerStarted = true
        requireActivity().stopService(restTimerServiceIntent)
        binding.resumeButton.isInvisible = true
        binding.pauseButton.isInvisible = false
        binding.primaryTimer.setTextColor(resources.getColor(R.color.white, null))
        binding.primaryTimer.text = binding.secondaryTimer.text
        binding.secondaryTimer.isInvisible = true
    }

    private fun startRestTimer() {
        registerReceiver(
            requireContext(),
            updateRestTimer,
            IntentFilter(DecreasingTimerService.TIMER_UPDATE),
            RECEIVER_NOT_EXPORTED
        )
        requireActivity().startService(restTimerServiceIntent)
    }

    private fun startRestTimerUp() {
        registerReceiver(
            requireContext(),
            updateRestTimer,
            IntentFilter(IncreasingTimerService.TIMER_UPDATE),
            RECEIVER_NOT_EXPORTED
        )
        requireActivity().startService(restTimerServiceIntent)
    }


    // TODO: este método será utilizado quando o usuário finalizar o treino
    //  @Suppress("unused")
    private fun stopTimer() {

        requireActivity().stopService(restTimerServiceIntent)
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


    private val updateRestTimer: BroadcastReceiver = object : BroadcastReceiver() {
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