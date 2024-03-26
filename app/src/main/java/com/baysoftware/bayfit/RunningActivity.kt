package com.baysoftware.bayfit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.baysoftware.bayfit.databinding.ActivityRunningBinding

class RunningActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRunningBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityRunningBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}