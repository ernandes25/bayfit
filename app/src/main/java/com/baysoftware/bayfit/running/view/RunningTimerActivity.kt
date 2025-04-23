package com.baysoftware.bayfit.running.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.registerReceiver
import androidx.core.content.getSystemService
import androidx.core.os.bundleOf
import androidx.core.view.isInvisible
import androidx.lifecycle.lifecycleScope
import com.baysoftware.bayfit.R
import com.baysoftware.bayfit.databinding.ActivityRunningTimerBinding
import com.baysoftware.bayfit.preferences.UserManager
import com.baysoftware.bayfit.running.service.DecreasingTimerService
import com.baysoftware.bayfit.running.service.IncreasingTimerService
import com.baysoftware.bayfit.running.service.TimerService
import com.baysoftware.bayfit.running.service.TotalTimerService
import com.baysoftware.bayfit.util.getTimeStringFromDouble
import kotlinx.coroutines.launch

class RunningTimerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRunningTimerBinding
    private lateinit var totalTimerServiceIntent: Intent
    private lateinit var restTimerServiceIntent: Intent
    private lateinit var timerMode: UserManager.TimerMode
    private var broadcastReceivers = mutableListOf<BroadcastReceiver>()
    private val viewModel: RunningTimerViewModel by viewModels {
        RunningTimerViewModel.provideFactory(this.application, this)
    }

    private var totalRestTime = 0.00
    private var restTime = 0.00
    private var increasingTime = 0.00
    private var isResting = false

    private val updateTotalTimerBroadcastReceiver: BroadcastReceiver =
        object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                increasingTime = intent.getDoubleExtra(TimerService.TIME_EXTRA, 0.0)
                val time = increasingTime.getTimeStringFromDouble()
                Log.d("TimerFragment", "Increasing Time: $increasingTime")
                Log.d("TimerFragment", "Formatted Time: $time")

                if (isResting) {
                    binding.secondaryTimer.text = time
                } else {
                    binding.primaryTimer.text = time
                }
            }
        }

    private val updateRestTimerBroadcastReceiver: BroadcastReceiver =
        object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val time = intent.getDoubleExtra(TimerService.TIME_EXTRA, 0.0)

                if (timerMode == UserManager.TimerMode.PREDEFINED && time == 0.00) {
                    stopTimer()
                    binding.resumeButton.isInvisible = false
                    binding.textRest.isInvisible = true
                    vibrate()
                }

                if (time <= 5.0 && time > 0.0) {
                    vibrate(500)
                }

                totalRestTime++

                binding.primaryTimer.text = time.getTimeStringFromDouble()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRunningTimerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupTimer()
        setupRestTimer()
        setupListeners()
    }

    private fun setupTimer() {
        // Iniciando contador de tempo total
        totalTimerServiceIntent = Intent(this, TotalTimerService::class.java)
        totalTimerServiceIntent.putExtra(TimerService.TIME_EXTRA, increasingTime)
        registerReceiver(
            this,
            updateTotalTimerBroadcastReceiver,
            IntentFilter(TotalTimerService.TIMER_UPDATE),
            ContextCompat.RECEIVER_NOT_EXPORTED
        )
        this.startService(totalTimerServiceIntent)
        broadcastReceivers.add(updateTotalTimerBroadcastReceiver)

        viewModel.startSession()
    }

    private fun setupRestTimer() {
        // Iniciando contador de tempo de descanso
        lifecycleScope.launch {
            timerMode = UserManager.getInstance().readTimerMode(this@RunningTimerActivity)
            when (timerMode) {
                UserManager.TimerMode.FREE,
                UserManager.TimerMode.UNDEFINED -> {
                    // Se o tempo for livre, o intent tem que ser configurado para que o serviço seja
                    // CRESCENTE, portanto não há necessidade de consultar o DataStore para obter tempo
                    // informado pelo usuário.
                    restTimerServiceIntent =
                        Intent(this@RunningTimerActivity, IncreasingTimerService::class.java)
                    restTimerServiceIntent.putExtra(TimerService.TIME_EXTRA, 0.0)
                }

                UserManager.TimerMode.PREDEFINED -> {
                    // Se o tempo for pré-definido, o intent tem que ser configurado para que o serviço
                    // seja DECRESCENTE, e o valor padrão deve ser obtido do DataStore, que por sua vez
                    // foi informado pelo usuário.
                    val timerConfiguration =
                        UserManager.getInstance().readTimerConfiguration(this@RunningTimerActivity)
                    restTime =
                        timerConfiguration.minute.toDouble() * 60 + timerConfiguration.second.toDouble()
                    restTimerServiceIntent =
                        Intent(this@RunningTimerActivity, DecreasingTimerService::class.java)
                    restTimerServiceIntent.putExtra(TimerService.TIME_EXTRA, restTime)
                }
            }
        }
    }

    private fun setupListeners() {
        binding.resumeButton.setOnLongClickListener {
            finishTrainingSession()
            true
        }

        binding.pauseButton.setOnLongClickListener {
            finishTrainingSession()
            true
        }

        binding.pauseButton.setOnClickListener { pauseTimer() }
        binding.resumeButton.setOnClickListener { resumeTraining() }
    }

    private fun vibrate(duration: Long = 500) {
        val vibrator = this.getSystemService() as? Vibrator
        vibrator?.vibrate(
            VibrationEffect.createOneShot(
                duration,
                VibrationEffect.DEFAULT_AMPLITUDE
            )
        )
    }

    private fun pauseTimer() {
        isResting = true
        if (timerMode == UserManager.TimerMode.FREE) {
            startRestTimer(IncreasingTimerService.TIMER_UPDATE)

            // Atualizações de visualização
            binding.pauseButton.isInvisible = true
            binding.resumeButton.isInvisible = false
            binding.primaryTimer.setTextColor(resources.getColor(R.color.green, null))
            binding.secondaryTimer.isInvisible = false
            binding.secondaryTimer.text = binding.primaryTimer.text
        } else {
            startRestTimer(DecreasingTimerService.TIMER_UPDATE)

            // Atualizações de visualização
            binding.textRest.isInvisible = false
            binding.pauseButton.isInvisible = true
            binding.primaryTimer.setTextColor(resources.getColor(R.color.green, null))
            binding.secondaryTimer.isInvisible = false
            binding.secondaryTimer.text = binding.primaryTimer.text
        }
    }

    private fun resumeTraining() {
        isResting = false
        this.stopService(restTimerServiceIntent)
        broadcastReceivers.remove(updateRestTimerBroadcastReceiver)

        binding.resumeButton.isInvisible = true
        binding.pauseButton.isInvisible = false
        binding.primaryTimer.setTextColor(resources.getColor(R.color.white, null))
        binding.primaryTimer.text = binding.secondaryTimer.text
        binding.secondaryTimer.isInvisible = true
    }

    private fun startRestTimer(timerService: String) {
        registerReceiver(
            this,
            updateRestTimerBroadcastReceiver,
            IntentFilter(timerService),
            ContextCompat.RECEIVER_NOT_EXPORTED
        )
        this.startService(restTimerServiceIntent)
        broadcastReceivers.add(updateRestTimerBroadcastReceiver)
    }

    private fun stopTimer() {
        this.stopService(restTimerServiceIntent)
        this.broadcastReceivers.remove(updateRestTimerBroadcastReceiver)
        isResting = true
    }

    private fun finishTrainingSession() {
        // Encerra os serviços que fornecem atualizações de tempo
        this.stopService(totalTimerServiceIntent)
        this.stopService(restTimerServiceIntent)
        broadcastReceivers.forEach { receiver ->
            this.unregisterReceiver(receiver)
        }

        // Chama o ViewModel, que irá salvar a sessão de exercícios no banco de dados
        viewModel.saveSession(System.currentTimeMillis(), totalRestTime.toLong())

        // Navega para a tela de resultados
        val bundle = bundleOf(

            /*    END_TIME to binding.secondaryTimer.text,
                END_REST to totalRestTime*/

            END_TIME to increasingTime.getTimeStringFromDouble(),  // Modificação aqui(20/11)
            END_REST to totalRestTime
        )
        val intent = Intent(this, RunningResultActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
        finish()
    }
}