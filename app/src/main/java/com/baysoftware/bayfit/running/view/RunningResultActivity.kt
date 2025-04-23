package com.baysoftware.bayfit.running.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.baysoftware.bayfit.databinding.ActivityRunningResultBinding
import com.baysoftware.bayfit.util.getTimeStringFromDouble

const val END_TIME = "endTime"
const val END_REST = "endRest"

class RunningResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRunningResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRunningResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.buttonReturn.setOnClickListener { finish() }
        binding.timeEnd.text = intent.getStringExtra(END_TIME)
        binding.restEnd.text = intent.getDoubleExtra(END_REST, 0.0).getTimeStringFromDouble()
    }
}