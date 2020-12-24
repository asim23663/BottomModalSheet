package com.asim.bottomsheet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.asim.bottomsheet.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.layout.cardView.setOnClickListener(View.OnClickListener {
            BottomSheetFragment().apply {
                show(supportFragmentManager, tag)
            }
        })
    }
}