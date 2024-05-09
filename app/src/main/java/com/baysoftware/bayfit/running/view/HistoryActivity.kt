package com.baysoftware.bayfit.running.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.baysoftware.bayfit.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}