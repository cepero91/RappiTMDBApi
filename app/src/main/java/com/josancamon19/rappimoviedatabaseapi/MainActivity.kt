package com.josancamon19.rappimoviedatabaseapi

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.josancamon19.rappimoviedatabaseapi.databinding.ActivityMainBinding
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity() {

    private  lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =DataBindingUtil.setContentView(this,R.layout.activity_main)
    }

}
