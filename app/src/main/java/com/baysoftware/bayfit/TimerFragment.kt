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
import android.widget.NumberPicker
import androidx.core.content.ContextCompat.RECEIVER_NOT_EXPORTED
import androidx.core.content.ContextCompat.registerReceiver
import androidx.core.content.getSystemService
import androidx.core.os.bundleOf
import androidx.core.view.isInvisible
import androidx.databinding.DataBindingUtil
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStore
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.liveData
import androidx.navigation.fragment.findNavController
import com.baysoftware.bayfit.databinding.FragmentTimerBinding
import com.google.android.gms.common.internal.service.Common
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.prefs.Preferences
import kotlin.math.roundToInt

class TimerFragment : Fragment() {

    private lateinit var binding: FragmentTimerBinding
    private lateinit var increasingTimerServiceIntent: Intent
    private lateinit var decreasingTimerServiceIntent: Intent


    private var increasingTime = 0.00
    //private var decreasingTime = 4.00 /*11/03/2024-Comentado em 11/03/2024*/
 //   private var decreasingTime = qualquerCoisa()


   //private var decreasingTime = UserManager.SECOND_KEY
//    private var decreasingTime = dataStore().DataStore{UserManager.SECOND_KEY}
   //private var decreasingTime = dataStore(preferencesDataStore(UserManager.SECOND_KEY))
 //private var decreasingTime = preferencesDataStore(UserManager.getInstance().readDataUser())
    private var decreasingTime = preferencesDataStore(UserManager.getInstance().readDataUser())

 //   private var decreasingTime = dataStore(preferencesDataStore("settings")



     private fun qualquerCoisa(){
         var second = UserManager.SECOND_KEY
         return qualquerCoisa()

   }


    private var timerStarted = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_timer, container, false)
        increasingTimerServiceIntent = Intent(requireContext(), IncreasingTimerService::class.java)
        increasingTimerServiceIntent.putExtra(TimerService.TIME_EXTRA, increasingTime)
        decreasingTimerServiceIntent = Intent(requireContext(), DecreasingTimerService::class.java)
        decreasingTimerServiceIntent.putExtra(
            TimerService.TIME_EXTRA,
            decreasingTime
        ) // 11/03/2024-Comentado

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
        UserManager.SECOND_KEY

        requireActivity().startService(increasingTimerServiceIntent)

    }



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
            //    decreasingTime = intent.getDoubleExtra(TimerService.TIME_EXTRA, 0.0)


            if (decreasingTime == 0.00) {    //11/03/2024
                stopTimer()
                binding.primaryTimer.text
                binding.resumeButton.isInvisible = false
                binding.textRest.isInvisible = true
                vibrate()

                // TODO: parar tempo (chamar métodos "stopService" e possivelmente "unregisterReceiver")
                // TODO: esconder "Descanso"
                // TODO: mostrar botão play verde
            }
            //binding.primaryTimer.text = Preferences.importPreferences(UserManager)(decreasingTime)
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