package com.baysoftware.bayfit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.baysoftware.bayfit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // if (sharedpreferences.ongoingSession == true) {
        // findNavController().navigate(R.id.action_fragmentA_to_fragmentB)
        // }
        //TODO: ler das SharedPreferences se há uma contagem acontecendo ou não
        // ongoingSession = true ou false?
        // se true, navegar para TimerFragment
        // se false, navegar para HomeFragment
    }
}