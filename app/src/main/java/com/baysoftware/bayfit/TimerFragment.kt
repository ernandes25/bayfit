package com.baysoftware.bayfit

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.RECEIVER_NOT_EXPORTED
import androidx.core.content.ContextCompat.registerReceiver
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.baysoftware.bayfit.databinding.FragmentTimerBinding
import kotlin.math.roundToInt

class TimerFragment : Fragment() {

    private lateinit var binding: FragmentTimerBinding
    private lateinit var serviceIntent: Intent
    private var time = 0.0
    var timerStarted = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_timer, container, false)
        serviceIntent = Intent(requireContext(), TimerService::class.java)
        registerReceiver(
            requireContext(),
            updateTime,
            IntentFilter(TimerService.TIMER_UPDATE),
            RECEIVER_NOT_EXPORTED
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.controlButton.setOnClickListener { startStopTimer() }
        binding.controlButton.setOnLongClickListener { resetTimer() }
        startStopTimer()
    }

    private fun resetTimer(): Boolean {
        stopTimer()
        time = 0.0
        binding.timeTV.text = getTimeStringFromDouble(time)
        return true
    }

    private fun startStopTimer() {
        if (timerStarted)
            stopTimer()
        else
            startTimer()
    }

    private fun startTimer() {
        serviceIntent.putExtra(TimerService.TIME_EXTRA, time)
        requireActivity().startService(serviceIntent)
        binding.controlButton.setImageResource(R.drawable.ic_pause)
        timerStarted = true
    }

    private fun stopTimer() {
        requireActivity().stopService(serviceIntent)
        binding.controlButton.setImageResource(R.drawable.ic_continue)
        timerStarted = false
    }
    private val updateTime: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            time = intent.getDoubleExtra(TimerService.TIME_EXTRA, 0.0)
            binding.timeTV.text = getTimeStringFromDouble(time)
        }
    }

    private fun getTimeStringFromDouble(time: Double): String {
        val resultInt = time.roundToInt()
        val hours = resultInt % TOTAL_MINUTES_IN_DAY / TOTAL_MINUTES_IN_HOUR
        val minutes = resultInt % TOTAL_MINUTES_IN_DAY % TOTAL_MINUTES_IN_HOUR / TOTAL_HOURS
        val seconds = resultInt % TOTAL_MINUTES_IN_DAY % TOTAL_MINUTES_IN_HOUR % TOTAL_HOURS
        return makeTimeString(hours, minutes, seconds)
    }

    private fun makeTimeString(hour: Int, min: Int, sec: Int): String =
        String.format("%02d:%02d:%02d", hour, min, sec)

    companion object{
        const val TOTAL_MINUTES_IN_DAY = 86400
        const val TOTAL_MINUTES_IN_HOUR = 3600
        const val TOTAL_HOURS = 60
    }
}